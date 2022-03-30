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


    public List<Person> createPerson(Person person) {
        return personRepository.create(person);
    }

    public void delete(Person personDelete) {
        personRepository.delete(personDelete);
    }

    public List<Person> update(Person personUpdate) {
        return personRepository.update(personUpdate);
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

    public int ageCalculation(String birthdate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthdate1 = LocalDate.parse(birthdate, dtf);
        LocalDate today = LocalDate.now();
        return Period.between(birthdate1, today).getYears();
    }

    public Map<String, Object> countAdultAndChild(int station) throws IOException {
        Map<String, MedicalRecord> medicalRecordMap = stringMedicalRecordMap();
        Map<String, Object> adultAndChild = new HashMap<>();
        List<Person> getPersonByStationAddress = getPersonByStationAddress(station);
        List<Person> adult = new ArrayList<>();
        List<Person> child = new ArrayList<>();
        for (Person persons : getPersonByStationAddress) {
            String birthdate = medicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            int age = ageCalculation(birthdate);
            if (age > 18) {
                adult.add(persons);
            } else {
                child.add(persons);
            }
        }
        List<PersonDto> personDto = getPersonByStationAddress.stream().map(Person::toPersonDto).collect(Collectors.toList());

        adultAndChild.put("personOverEighteen", adult.size());
        adultAndChild.put("personUnderEighteen", child.size());
        adultAndChild.put("personByStationAddress", personDto);
        return adultAndChild;
    }
    public List<Person> getPersonByAddress (String address){
        return personRepository.findAll()
                .stream()
                .filter(e->e.getAddress().equals(address))
                .collect(Collectors.toList());
    }


    public Map<String,Object> getChildAndFamilyByAddress(String address) {
        Map<String, MedicalRecord> medicalRecordMap = stringMedicalRecordMap();
        Map<String, Object> result = new HashMap<>();

        List<Person> getPersonByAddress = getPersonByAddress(address);

        List<Person> other = new ArrayList<>();
        List<ChildDto> child = new ArrayList<>();
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
        result.put("other",other);
        result.put("child", child);
        return result;

    }
    public Map<String,Set<String>> getPersonPhoneByStation(int station) throws IOException {
        Map<String,Set<String>> result = new HashMap<>();
        List<Person> personByStationAddress = getPersonByStationAddress(station);
        Set<String> phone = new HashSet<>();
        for (Person person : personByStationAddress){
            phone.add(person.getPhone());
        }
        result.put("PhoneAlert",phone);
        return result;
    }

    public Map<String, List<PersonWithMedicalRecordAndAgeDto>> getPersonAndMedicalRecordPerAddress(String address){
        Map<String,MedicalRecord> stringMedicalRecordMap = stringMedicalRecordMap();
        List<Person> personByAddress = getPersonByAddress(address);
        List<String> stationByAddress = fireStationService.getStationByAddress(address);
        List<PersonWithMedicalRecordAndAgeDto> personWithMedicalRecordAndAgeDtos = new ArrayList<>();
        for (Person persons : personByAddress) {
            String birthdate = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            int age = ageCalculation(birthdate);
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

    public Map<String, List<FamilyByStation>> getFamilyByStation(int station) throws IOException {
        List<String> addresses =fireStationService.getAddressByStationNumber(station);
        Map<String, List<FamilyByStation>> result = new HashMap<>();
        Map<String,MedicalRecord> stringMedicalRecordMap = stringMedicalRecordMap();
        List<FamilyByStation> familyByStations;
        for(String address : addresses){
            List<Person> personList = getPersonByAddress(address);
            familyByStations = new ArrayList<>();
            for (Person persons : personList) {
                String birthdate = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
                int age = ageCalculation(birthdate);
                List<String> medications = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getMedications();
                List<String> allergies = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getAllergies();
                FamilyByStation personOfFamily = new FamilyByStation(persons.getFirstName(), persons.getLastName(), persons.getPhone(), age,medications,allergies);
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

    public Map <String,List<PersonWithMedicalAndEmail>> getPersonWithMedicalAndEmail(){
        List<Person> personList = personRepository.findAll();
        List<PersonWithMedicalAndEmail> personWithMedicalAndEmailList = new ArrayList<>();
        Map<String,MedicalRecord> stringMedicalRecordMap = stringMedicalRecordMap();
        Map<String,List<PersonWithMedicalAndEmail>> result = new HashMap<>();
        for(Person persons : personList){
            String birthdate = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            List<String> medications = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getMedications();
            List<String> allergies = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getAllergies();
            int age = ageCalculation(birthdate);
            PersonWithMedicalAndEmail person = new PersonWithMedicalAndEmail(persons.getFirstName(),persons.getLastName(), persons.getAddress(),persons.getEmail(),age,medications,allergies);
            personWithMedicalAndEmailList.add(person);
        }
        result.put("personWithMedicalAndEmailList",personWithMedicalAndEmailList);
        return result;
    }
}
