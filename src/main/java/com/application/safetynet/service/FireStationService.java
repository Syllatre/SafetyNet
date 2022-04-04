package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.CountChildAndAdult;
import com.application.safetynet.model.dto.FireStationDto;
import com.application.safetynet.model.dto.PersonDto;
import com.application.safetynet.repository.FireStationRepository;
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
public class FireStationService {

    @Autowired
    FireStationRepository fireStationRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;


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
        for (int element : stationNumber ){
            List <String> addresses= fireStationsList
                    .stream().
                    filter(station -> Integer.parseInt(station.getStation()) == element)
                    .flatMap(station -> station.getAddresses().stream())
                    .collect(Collectors.toList());
            for(String address : addresses){
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

    public CountChildAndAdult countAdultAndChild(int station) throws IOException {
        Map<String, MedicalRecord> medicalRecordMap = stringMedicalRecordMap();
        List<Person> getPersonByStationAddress = getPersonByStationAddress(station);
        CountChildAndAdult countChildAndAdult;
        int ageUnderEighteen=0;
        int ageOverEighteen=0;
        for (Person persons : getPersonByStationAddress) {
            String birthdate = medicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
            int age = ageCalculation(birthdate);
            if (age > 18) {
                ageOverEighteen = ageOverEighteen +1;
            } else {
                ageUnderEighteen = ageUnderEighteen +1;
            }
        }
        List<PersonDto> personDto = getPersonByStationAddress.stream().map(Person::toPersonDto).collect(Collectors.toList());
        countChildAndAdult = new CountChildAndAdult(personDto,ageUnderEighteen,ageOverEighteen);
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

    public List<Person> getPersonByAddress (String address){
        return personRepository.findAll()
                .stream()
                .filter(e->e.getAddress().equals(address))
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
}
