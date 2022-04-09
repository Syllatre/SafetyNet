package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.ChildAlertDto;
import com.application.safetynet.model.dto.FamilyMemberDto;
import com.application.safetynet.model.dto.PersonEmailDto;
import com.application.safetynet.model.dto.PhoneAlertDto;
import com.application.safetynet.repository.FireStationRepository;
import com.application.safetynet.repository.MedicalRecordsRepository;
import com.application.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    MedicalRecordsRepository medicalRecordsRepository;

    @Mock
    FireStationService fireStationService;

    @Mock
    FireStationRepository fireStationRepository;

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    private static final Map<String, MedicalRecord> medicalRecordMap =
            Map.of("Jacob Boyd", MedicalRecord.builder().firstName("John").lastName("Boyd").birthdate("03/06/1984").allergies(List.of("nillacilan")).medications(List.of("aznol:350mg", "hydrapermazol:100mg")).build(),
                    "Tenley Boyd", MedicalRecord.builder().firstName("Tenley").lastName("Boyd").birthdate("03/06/2012").allergies(List.of("peanut")).medications(List.of()).build(),
                    "Ron Peters", MedicalRecord.builder().firstName("Ron").lastName("Peters").birthdate("04/06/1965").allergies(List.of()).medications(List.of()).build(),
                    "Lily Cooper", MedicalRecord.builder().firstName("Lily").lastName("Cooper").birthdate("03/06/1994").allergies(List.of("nillacilan")).medications(List.of()).build(),
                    "Brian Stelzer", MedicalRecord.builder().firstName("Brian").lastName("Stelzer").birthdate("12/06/1975").allergies(List.of("nillacilan")).medications(List.of("ibupurin:200mg", "hydrapermazol:400mg")).build(),
                    "Allisson Boyd", MedicalRecord.builder().firstName("Allison").lastName("Boyd").birthdate("03/15/1965").allergies(List.of("nillacilan")).medications(List.of("aznol:350mg")).build());


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

    private static final List<Person> getPersonByAddressList = List.of(Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com").build(),
            Person.builder().firstName("Jacob").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6513").email("drk@email.com").build(),
            Person.builder().firstName("Tenley").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512").email("tenz@email.com").build());

    private static final List<FireStation> fireStationList = List.of(FireStation.builder().station("1").addresses(List.of("908 73rd St", "947 E. Rose Dr", "644 Gershwin Cir")).build(),
            FireStation.builder().station("2").addresses(List.of("29 15th St", "892 Downing Ct", "951 LoneTree Rd")).build(),
            FireStation.builder().station("3").addresses(List.of("834 Binoc Ave", "748 Townings Dr", "112 Steppes Pl", "1509 Culver St")).build(),
            FireStation.builder().station("4").addresses(List.of("112 Steppes Pl", "489 Manchester St")).build());


    @BeforeEach
    void setUp() {
    }

    @Test
    void getChildAndFamilyByAddressTest() {
        when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordList);
        when(fireStationService.getPersonByAddress("1509 Culver St")).thenReturn(getPersonByAddressList);
        ChildAlertDto childAndFamilyByAddressDto = personService.getChildAndFamilyByAddress("1509 Culver St");
        System.out.println(childAndFamilyByAddressDto);
        List<FamilyMemberDto> familyMembers = childAndFamilyByAddressDto.getFamilyMembers();
        assertEquals(childAndFamilyByAddressDto.getFirstName(), "Tenley");
        assertEquals(familyMembers.size(), 2);
    }

    @Test
    void getPersonPhoneByStationTest() throws IOException {
        when(personRepository.findAll()).thenReturn(personList);
        List<String> addresses = new ArrayList();
        addresses.add("834 Binoc Ave");
        addresses.add("748 Townings Dr");
        addresses.add("112 Steppes Pl");
        when(fireStationService.getAddressByStationNumber(3)).thenReturn(addresses);
        List<PhoneAlertDto> getPersonPhoneByStation = personService.getPersonPhoneByStation(3);
        assertEquals(getPersonPhoneByStation.size(), 2);

    }

    @Test
    void getPersonEmailTest() {
        when(personRepository.findAll()).thenReturn(personList);
        List<PersonEmailDto> getPersonEmail = personService.getPersonEmail("Culver");
        assertEquals(getPersonEmail.size(), 7);
        boolean emailPeter = false;
        for (PersonEmailDto element : getPersonEmail) {
            if (element.getEmail().equals("jpeter@email.com")) {
                emailPeter = true;
            }
        }
        assertTrue(emailPeter);
        System.out.println(getPersonEmail);
    }

    @Test
    void getPersonWithMedicalAndEmailWithLastNameTest() {
        when(personRepository.findAll()).thenReturn(personList);
        when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordList);
        List result = personService.getPersonWithMedicalAndEmail(null,"Boyd");
        System.out.println(result);
        assertEquals(result.size(), 4);
    }

    @Test
    void getPersonWithMedicalAndEmailTest() {
        when(personRepository.findAll()).thenReturn(personList);
        when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordList);
        List result = personService.getPersonWithMedicalAndEmail("John", "Boyd");
        System.out.println(result);
        assertEquals(result.size(), 1);
    }

    @Test
    void getPersonByStationAddressTest() throws IOException {
        List<String> addresses = new ArrayList();
        addresses.add("112 Steppes Pl");
        addresses.add("489 Manchester St");
        when(personRepository.findAll()).thenReturn(personList);
        when(fireStationService.getAddressByStationNumber(4)).thenReturn(addresses);
        List<Person> result = personService.getPersonByStationAddress(4);
        System.out.println(result);
        assertEquals(result.size(), 3);
        assertTrue(result.stream().anyMatch(e -> e.getFirstName().equals("Lily")));
        assertTrue(result.stream().anyMatch(e -> e.getFirstName().equals("Allison")));
        assertTrue(result.stream().anyMatch(e -> e.getFirstName().equals("Ron")));
        assertFalse(result.stream().anyMatch(e -> e.getFirstName().equals("Tenley")));
    }

    @Test
    void stringMedicalRecordMapTest() {
        when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordList);
        Map<String, MedicalRecord> medicalRecordMap = personService.stringMedicalRecordMap();
        assertNotNull(medicalRecordMap);
    }

    @Test
    void ageCalculationTest() {
        int age = personService.ageCalculation("03/25/2020");
        assertEquals(age, 2);
    }
}
