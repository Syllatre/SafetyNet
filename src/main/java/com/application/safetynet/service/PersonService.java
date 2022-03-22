package com.application.safetynet.service;


import com.application.safetynet.model.dto.ChildDto;
import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.PersonDto;
import com.application.safetynet.model.dto.PersonWithMedicalRecordAndAgeDto;
import com.application.safetynet.repository.MedicalRecordsRepository;
import com.application.safetynet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<Person> personListByAddress = personList.stream().filter(person -> addresses.contains(person.getAddress())).collect(Collectors.toList());
        return personListByAddress;
    }

    public int ageCalculation(String birthdate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthdate1 = LocalDate.parse(birthdate, dtf);
        LocalDate today = LocalDate.now();
        int age = Period.between(birthdate1, today).getYears();
        return age;
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
        List<Person> getPersonByAddress =  personRepository.findAll()
                .stream()
                .filter(e->e.getAddress().equals(address))
                .collect(Collectors.toList());
        return getPersonByAddress;
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
                    if(e.getFirstName() != persons.getFirstName())
                    other.add(e);
                });
            }
        }
        result.put("child", child);
        result.put("other",other);
        return result;

    }
    public Map<String,List<String>> getPersonPhoneByStation(int station) throws IOException {
        Map<String,List<String>> result = new HashMap<>();
        List<Person> personByStationAddress = getPersonByStationAddress(station);
        List<String> phone = personByStationAddress.stream().map(e-> e.getPhone()).collect(Collectors.toList());
        result.put("Phone Alert",phone);
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
}
