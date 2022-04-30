package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.*;
import com.application.safetynet.repository.FireStationRepository;
import com.application.safetynet.repository.MedicalRecordsRepository;
import com.application.safetynet.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class FireStationService {
    private static final Logger logger = LoggerFactory.getLogger(FireStationService.class);

    FireStationRepository fireStationRepository;

    PersonRepository personRepository;

    MedicalRecordsRepository medicalRecordsRepository;

    public FireStationService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordsRepository medicalRecordsRepository) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    public Optional<FireStation> getFireStation(final String station) {
        return fireStationRepository.findByStation(station);
    }


    public void deleteFireStations(FireStationDto id) {
        fireStationRepository.deleteFireStation(id);
    }

    public FireStation saveFireStation(FireStation fireStations) {
        FireStation savedFireStation = fireStationRepository.save(fireStations);
        return savedFireStation;
    }

    public List<String> getAddressByStationNumber(int stationNumber) throws IOException {
        List<FireStation> fireStationsList = fireStationRepository.findAll();
        return fireStationsList
                .stream().
                filter(station -> Integer.parseInt(station.getStation()) == stationNumber)
                .flatMap(station -> station.getAddresses().stream())
                .collect(Collectors.toList());
    }

    public List<String> getAddressByStationNumberList(List<Integer> stationNumber) throws IOException {
        List<FireStation> fireStationsList = fireStationRepository.findAll();
        List<String> result = new ArrayList<>();
        for (int element : stationNumber) {
            List<String> addresses = fireStationsList
                    .stream().
                    filter(station -> Integer.parseInt(station.getStation()) == element)
                    .flatMap(station -> station.getAddresses().stream())
                    .collect(Collectors.toList());
            for (String address : addresses) {
                result.add(address);
            }
        }
        return result;
    }

    public List<String> getStationByAddress(String address) {
        List<FireStation> fireStationsList = fireStationRepository.findAll();
        return fireStationsList
                .stream()
                .filter(station -> station.getAddresses().contains(address))
                .map(station -> station.getStation()).collect(Collectors.toList());
    }

    public CountChildAndAdultDto countAdultAndChild(int station) throws IOException {
        Map<String, MedicalRecord> medicalRecordMap = stringMedicalRecordMap();
        List<Person> getPersonByStationAddress = getPersonByStationAddress(station);
        CountChildAndAdultDto countChildAndAdult;
        int ageUnderEighteen = 0;
        int ageOverEighteen = 0;
        for (Person persons : getPersonByStationAddress) {
            String personId = persons.getFirstName() + " " + persons.getLastName();
            String birthdate = null;
            if(medicalRecordMap.containsKey(personId)) {
                MedicalRecord medicalRecord = medicalRecordMap.get(personId);
                birthdate = medicalRecord != null ? medicalRecord.getBirthdate() : null;
            }
            if(birthdate != null){
                int age = ageCalculation(birthdate);
                if (age > 18) {
                    ageOverEighteen = ageOverEighteen + 1;
                } else {
                    ageUnderEighteen = ageUnderEighteen + 1;
                }
            }
        }
        List<PersonDto> personDto = getPersonByStationAddress.stream().map(Person::toPersonDto).collect(Collectors.toList());
        countChildAndAdult = new CountChildAndAdultDto(personDto, ageUnderEighteen, ageOverEighteen);
        return countChildAndAdult;
    }


    public List<Person> getPersonByStationAddress(int station) throws IOException {
        List<Person> personList = personRepository.findAll();
        List<String> addresses = getAddressByStationNumber(station);
        return personList.stream().filter(person -> addresses.contains(person.getAddress())).collect(Collectors.toList());
    }

    public int ageCalculation(String birthdate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthdate1 = LocalDate.parse(birthdate, dtf);
        LocalDate today = LocalDate.now();
        return Period.between(birthdate1, today).getYears();
    }

    public List<Person> getPersonByAddress(String address) {
        return personRepository.findAll()
                .stream()
                .filter(e -> e.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    public Map<String, MedicalRecord> stringMedicalRecordMap() {
        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAll();
        Map<String, MedicalRecord> stringMedicalRecordMap = new HashMap<>();
        for (MedicalRecord medicalRecord : medicalRecords) {
            stringMedicalRecordMap.put(medicalRecord.getFirstName() + " " + medicalRecord.getLastName(), medicalRecord);
        }
        return stringMedicalRecordMap;
    }

    public List<FloodDto> getFamilyByStation(List<Integer> station) throws IOException {
        List<String> addresses = getAddressByStationNumberList(station);
        List<FloodDto> result = new ArrayList<>();
        Map<String, MedicalRecord> stringMedicalRecordMap = stringMedicalRecordMap();
        List<FamilyByStationDto> familyByStations;
        for (String address : addresses) {
            List<Person> personList = getPersonByAddress(address);
            familyByStations = new ArrayList<>();
            FloodDto otherFamily = new FloodDto();
            for (Person persons : personList) {
                String birthdate = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
                int age = ageCalculation(birthdate);
                List<String> medications = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getMedications();
                List<String> allergies = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getAllergies();
                FamilyByStationDto personOfFamily = new FamilyByStationDto(persons.getFirstName(), persons.getLastName(), persons.getPhone(), age, medications, allergies);
                familyByStations.add(personOfFamily);
            }
            FloodDto floodDto = new FloodDto(address, familyByStations);
            result.add(floodDto);
        }
        return result;
    }

    public FireDto getPersonAndMedicalRecordPerAddress(String address) {
        Map<String, MedicalRecord> stringMedicalRecordMap = stringMedicalRecordMap();
        List<Person> personByAddress = getPersonByAddress(address);
        List<String> stationByAddress = getStationByAddress(address);
        List<FamilyByStationDto> deservedPeopleList = new ArrayList<>();
        for (Person persons : personByAddress) {
            String birthdate = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            int age = ageCalculation(birthdate);
            List<String> medications = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getMedications();
            List<String> allergies = stringMedicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getAllergies();
            FamilyByStationDto deservedPeople =
                    new FamilyByStationDto(persons.getFirstName(), persons.getLastName(), persons.getPhone(), age, medications, allergies);
            deservedPeopleList.add(deservedPeople);
        }
        FireDto fireDto = new FireDto(deservedPeopleList, stationByAddress);
        return fireDto;
    }

    public List<FireStation> findAll(){
        return fireStationRepository.findAll();
    }
}
