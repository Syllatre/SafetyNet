package com.application.safetynet.service;


import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.*;
import com.application.safetynet.repository.MedicalRecordsRepository;
import com.application.safetynet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FireStationService fireStationService;

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;



    public Person createPerson(Person person) {
        return personRepository.create(person);
    }

    public void delete(Person personDelete) {
        personRepository.delete(personDelete);
    }

    public Person update(Person personUpdate) {
        return personRepository.update(personUpdate);
    }





    public ChildAlertDto getChildAndFamilyByAddress(String address) {
        Map<String, MedicalRecord> medicalRecordMap = stringMedicalRecordMap();
        List<Person> getPersonByAddress = fireStationService.getPersonByAddress(address);
        List<FamilyMemberDto> familyMembersList = new ArrayList<>();
        for(Person person : getPersonByAddress){
            String birthdate = medicalRecordMap.get(person.getFirstName() + " " + person.getLastName()).getBirthdate();
            int age = ageCalculation(birthdate);
            FamilyMemberDto familyMember = new FamilyMemberDto(person.getFirstName(), person.getLastName(), age);
            familyMembersList.add(familyMember);
        }
        ChildAlertDto childAlertDto=null;
        for (Person persons : getPersonByAddress) {
            String birthdate = medicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            int age = ageCalculation(birthdate);

            if (age <= 18) {
                List <FamilyMemberDto> memberFamily = new ArrayList<>();
                List <FamilyMemberDto> memberFamilyTemp = familyMembersList.stream().filter(e->e.getLastName().equals(persons.getLastName())).collect(Collectors.toList());
                memberFamilyTemp.forEach(e->{
                    if(!e.getFirstName().equals(persons.getFirstName()))
                    memberFamily.add(e);
                });
                childAlertDto = new ChildAlertDto(persons.getFirstName(), persons.getLastName(),age, memberFamily);

            }

        }
        return childAlertDto;
    }

    public List<PhoneAlertDto> getPersonPhoneByStation(int station) throws IOException {
        return getPersonByStationAddress(station)
                .stream()
                .map(Person::getPhone)
                .distinct()
                .map(PhoneAlertDto::new).collect(Collectors.toList());
    }

    public List<PersonEmailDto> getPersonEmail(String city){
        return personRepository.findAll().stream()
                .map(Person::getEmail)
                .distinct()
                .map(PersonEmailDto::new)
                .collect(Collectors.toList());
    }

    public List<PersonWithMedicalAndEmailDto> getPersonWithMedicalAndEmail(String firstName, String lastName){
        List<Person> personList = personRepository.findAll();
        List<PersonWithMedicalAndEmailDto> personWithMedicalAndEmailList = new ArrayList<>();
        Map<String,MedicalRecord> stringMedicalRecordMap = stringMedicalRecordMap();
        for(Person persons : personList){
            String birthdate = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            List<String> medications = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getMedications();
            List<String> allergies = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getAllergies();
            int age = fireStationService.ageCalculation(birthdate);
            PersonWithMedicalAndEmailDto person = new PersonWithMedicalAndEmailDto(persons.getFirstName(),persons.getLastName(), persons.getAddress(),persons.getEmail(),age,medications,allergies);
            personWithMedicalAndEmailList.add(person);
        }
        if(firstName ==null){
            List<PersonWithMedicalAndEmailDto> personWithFirstName =personWithMedicalAndEmailList.stream().filter(e-> e.getLastName().equals(lastName)).collect(Collectors.toList());
        return personWithFirstName;}
        else{
            List<PersonWithMedicalAndEmailDto> personWithFirstNameAndLastName = personWithMedicalAndEmailList
                    .stream()
                    .filter(e-> e.getLastName().equalsIgnoreCase(lastName)&&e.getFirstName().equalsIgnoreCase(firstName)).collect(Collectors.toList());
            return personWithFirstNameAndLastName;
        }
    }

    public int ageCalculation(String birthdate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthdate1 = LocalDate.parse(birthdate, dtf);
        LocalDate today = LocalDate.now();
        return Period.between(birthdate1, today).getYears();
    }

    public Map<String, MedicalRecord> stringMedicalRecordMap() {
        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAll();
        Map<String, MedicalRecord> stringMedicalRecordMap = new HashMap<>();
        for (MedicalRecord medicalRecord : medicalRecords) {
            stringMedicalRecordMap.put(medicalRecord.getFirstName() + " " + medicalRecord.getLastName(), medicalRecord);
        }
        return stringMedicalRecordMap;
    }

    public List<Person> getPersonByStationAddress(int station) throws IOException {
        List<Person> personList = personRepository.findAll();
        List<String> addresses = fireStationService.getAddressByStationNumber(station);
        return personList.stream().filter(person -> addresses.contains(person.getAddress())).collect(Collectors.toList());
    }
}
