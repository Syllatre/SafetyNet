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





    public ChildAndFamilyByAddressDto getChildAndFamilyByAddress(String address) {
        Map<String, MedicalRecord> medicalRecordMap = stringMedicalRecordMap();
        List<Person> getPersonByAddress = fireStationService.getPersonByAddress(address);
        List<Person> other = new ArrayList<>();
        List<ChildDto> child = new ArrayList<>();
        ChildAndFamilyByAddressDto result;

        for (Person persons : getPersonByAddress) {
            String birthdate = medicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            int age = ageCalculation(birthdate);
            if (age <= 18) {
                ChildDto childDto = new ChildDto(persons.getFirstName(), persons.getLastName(), age);
                child.add(childDto);
                List <Person> memberFamily = getPersonByAddress.stream().filter(e->e.getLastName().equals(persons.getLastName())).collect(Collectors.toList());
                memberFamily.forEach(e->{
                    if(!Objects.equals(e.getFirstName(), persons.getFirstName()))
                    other.add(e);
                });
            }
        }
        result = new ChildAndFamilyByAddressDto(other,child);
        return result;
    }

    public Map<String,Set<String>> getPersonPhoneByStation(int station) throws IOException {
        Map<String,Set<String>> result = new HashMap<>();
        List<Person> personByStationAddress = fireStationService.getPersonByStationAddress(station);
        Set<String> phone = new HashSet<>();
        for (Person person : personByStationAddress){
            phone.add(person.getPhone());
        }
        result.put("PhoneAlert",phone);
        return result;
    }

    public Map<String, List<PersonWithMedicalRecordAndAgeDto>> getPersonAndMedicalRecordPerAddress(String address){
        Map<String,MedicalRecord> stringMedicalRecordMap = fireStationService.stringMedicalRecordMap();
        List<Person> personByAddress = fireStationService.getPersonByAddress(address);
        List<String> stationByAddress = fireStationService.getStationByAddress(address);
        List<PersonWithMedicalRecordAndAgeDto> personWithMedicalRecordAndAgeDtos = new ArrayList<>();
        for (Person persons : personByAddress) {
            String birthdate = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            int age = fireStationService.ageCalculation(birthdate);
            List<String> medications = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getMedications();
            List<String> allergies = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getAllergies();
            PersonWithMedicalRecordAndAgeDto personDto =
                    new PersonWithMedicalRecordAndAgeDto(persons.getLastName(), persons.getPhone(), age,stationByAddress,medications,allergies);
            personWithMedicalRecordAndAgeDtos.add(personDto);
        }
        Map<String, List<PersonWithMedicalRecordAndAgeDto>> result =new HashMap<>();
        result.put("personWithMedicalRecordAndAge",personWithMedicalRecordAndAgeDtos);
        return result;
    }

    public Map<String, List<FamilyByStationDto>> getFamilyByStation(int station) throws IOException {
        List<String> addresses =fireStationService.getAddressByStationNumber(station);
        Map<String, List<FamilyByStationDto>> result = new HashMap<>();
        Map<String,MedicalRecord> stringMedicalRecordMap = fireStationService.stringMedicalRecordMap();
        List<FamilyByStationDto> familyByStations;
        for(String address : addresses){
            List<Person> personList = fireStationService.getPersonByAddress(address);
            familyByStations = new ArrayList<>();
            for (Person persons : personList) {
                String birthdate = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
                int age = fireStationService.ageCalculation(birthdate);
                List<String> medications = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getMedications();
                List<String> allergies = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getAllergies();
                FamilyByStationDto personOfFamily = new FamilyByStationDto(persons.getFirstName(), persons.getLastName(), persons.getPhone(), age,medications,allergies);
                familyByStations.add(personOfFamily);
            }
            result.put(address,familyByStations);
        }
        return result;
    }

    public Set<String> getPersonEmail(){
        List<Person> personList = personRepository.findAll();
        Set<String> email = new HashSet<>();
        for(Person person : personList){
            email.add(person.getEmail());
        }
        return email;
    }

    public Map <String,List<PersonWithMedicalAndEmailDto>> getPersonWithMedicalAndEmail(){
        List<Person> personList = personRepository.findAll();
        List<PersonWithMedicalAndEmailDto> personWithMedicalAndEmailList = new ArrayList<>();
        Map<String,MedicalRecord> stringMedicalRecordMap = fireStationService.stringMedicalRecordMap();
        Map<String,List<PersonWithMedicalAndEmailDto>> result = new HashMap<>();
        for(Person persons : personList){
            String birthdate = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            List<String> medications = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getMedications();
            List<String> allergies = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getAllergies();
            int age = fireStationService.ageCalculation(birthdate);
            PersonWithMedicalAndEmailDto person = new PersonWithMedicalAndEmailDto(persons.getFirstName(),persons.getLastName(), persons.getAddress(),persons.getEmail(),age,medications,allergies);
            personWithMedicalAndEmailList.add(person);
        }
        result.put("personWithMedicalAndEmailList",personWithMedicalAndEmailList);
        return result;
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
}
