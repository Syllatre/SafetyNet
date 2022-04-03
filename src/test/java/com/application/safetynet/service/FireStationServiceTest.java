package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.CountChildAndAdult;
import com.application.safetynet.model.dto.PersonDto;
import com.application.safetynet.repository.FireStationRepository;
import com.application.safetynet.repository.MedicalRecordsRepository;
import com.application.safetynet.repository.PersonRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    FireStationRepository fireStationRepository;

    @Mock
    PersonRepository personRepository;

    @Mock
    MedicalRecordsRepository medicalRecordsRepository;

    @InjectMocks
    FireStationService fireStationService;

        private static final List<MedicalRecord> medicalRecordList = List.of(MedicalRecord.builder().firstName("John").lastName("Boyd").birthdate("03/06/1984").allergies(List.of("nillacilan")).medications(List.of("aznol:350mg", "hydrapermazol:100mg")).build(),
            MedicalRecord.builder().firstName("Jacob").lastName("Boyd").birthdate("03/06/1989").allergies(List.of()).medications(List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")).build(),
            MedicalRecord.builder().firstName("Tenley").lastName("Boyd").birthdate("03/06/2012").allergies(List.of("peanut")).medications(List.of()).build(),
            MedicalRecord.builder().firstName("Ron").lastName("Peters").birthdate("04/06/1965").allergies(List.of()).medications(List.of()).build(),
            MedicalRecord.builder().firstName("Lily").lastName("Cooper").birthdate("03/06/1994").allergies(List.of("nillacilan")).medications(List.of()).build(),
            MedicalRecord.builder().firstName("Brian").lastName("Stelzer").birthdate("12/06/1975").allergies(List.of("nillacilan")).medications(List.of("ibupurin:200mg", "hydrapermazol:400mg")).build(),
            MedicalRecord.builder().firstName("Allison").lastName("Boyd").birthdate("03/15/1965").allergies(List.of("nillacilan")).medications(List.of("aznol:350mg")).build());

    private static final List<Person> personList = List.of(Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com").build(),
            Person.builder().firstName("Jacob").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6513").email("drk@email.com").build(),
            Person.builder().firstName("Tenley").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512").email("tenz@email.com").build(),
            Person.builder().firstName("Ron").lastName("Peters").address("112 Steppes Pl").city("Culver").zip("97451").phone("841-874-8888").email("jpeter@email.com").build(),
            Person.builder().firstName("Lily").lastName("Cooper").address("489 Manchester St").city("Culver").zip("97451").phone("841-874-9845").email("lily@email.com").build(),
            Person.builder().firstName("Brian").lastName("Stelzer").address("947 E. Rose Dr").city("Culver").zip("97451").phone("841-874-7784").email("bstel@email.com").build(),
            Person.builder().firstName("Allison").lastName("Boyd").address("112 Steppes Pl").city("Culver").zip("97451").phone("841-874-9888").email("aly@imail.com").build());

    private static final List<FireStation> fireStationList = List.of(FireStation.builder().station("1").addresses(List.of("908 73rd St", "947 E. Rose Dr", "644 Gershwin Cir")).build(),
            FireStation.builder().station("2").addresses(List.of("29 15th St", "892 Downing Ct", "951 LoneTree Rd")).build(),
            FireStation.builder().station("3").addresses(List.of("834 Binoc Ave", "748 Townings Dr", "112 Steppes Pl", "1509 Culver St")).build(),
            FireStation.builder().station("4").addresses(List.of("112 Steppes Pl", "489 Manchester St")).build());


    @Test
    void getAddressByStationNumberTest() throws IOException {
        when(fireStationRepository.findAll()).thenReturn(fireStationList);
        List<String> getAddressByStationNumber = fireStationService.getAddressByStationNumber(2);
        assertEquals(getAddressByStationNumber.size(),3);
        assertTrue(getAddressByStationNumber.contains("29 15th St"));
        System.out.println(getAddressByStationNumber);

    }

    @Test
    void getStationByAddressTest() throws IOException {
        when(fireStationRepository.findAll()).thenReturn(fireStationList);
        List<String> getStationByAddress = fireStationService.getStationByAddress("112 Steppes Pl");
        assertEquals(getStationByAddress.size(),2);
        assertTrue(getStationByAddress.contains("3"));
        assertTrue(getStationByAddress.contains("4"));

    }

    @Test
    void countAdultAndChildTest() throws IOException {
        when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordList);
        when(personRepository.findAll()).thenReturn(personList);
        List<String> addresses = new ArrayList();

        when(fireStationRepository.findAll()).thenReturn(fireStationList);
        CountChildAndAdult countAdultAndChild = fireStationService.countAdultAndChild(4);
        System.out.println(countAdultAndChild);
        assertEquals(countAdultAndChild.getPersonOverEighteen(),3);
        assertEquals(countAdultAndChild.getPersonUnderEighteen(),0);


    }

    @Test
    void stringMedicalRecordMapTest() {
        when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordList);
        Map<String, MedicalRecord> medicalRecordMap = fireStationService.stringMedicalRecordMap();
        assertNotNull(medicalRecordMap);
    }

    @Test
    void ageCalculationTest() {
        int age = fireStationService.ageCalculation("03/25/2020");
        assertEquals(age, 2);
    }


    @Test
    void getPersonByAddressTest() {
        when(personRepository.findAll()).thenReturn(personList);
        List<Person> getPersonByAddress = fireStationService.getPersonByAddress("1509 Culver St");
        assertEquals(getPersonByAddress.size(), 3);
        assertTrue(getPersonByAddress.stream().anyMatch(e -> e.getFirstName().equals("Jacob")));
    }

     @Test
    void getPersonByStationAddressTest() throws IOException {
        List<String> addresses = new ArrayList();
        addresses.add("112 Steppes Pl");
        addresses.add("489 Manchester St");
        when(personRepository.findAll()).thenReturn(personList);
        when(fireStationRepository.findAll()).thenReturn(fireStationList);
        List<Person> result = fireStationService.getPersonByStationAddress(4);
        assertEquals(result.size(), 3);
        assertTrue(result.stream().anyMatch(e -> e.getFirstName().equals("Lily")));
        assertTrue(result.stream().anyMatch(e -> e.getFirstName().equals("Allison")));
        assertTrue(result.stream().anyMatch(e -> e.getFirstName().equals("Ron")));
        assertFalse(result.stream().anyMatch(e -> e.getFirstName().equals("Tenley")));
    }
}
