package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.*;
import com.application.safetynet.repository.MedicalRecordsRepository;
import com.application.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    MedicalRecordsRepository medicalRecordsRepository;

    @Mock
    FireStationService fireStationService;

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    private static final Map<String,MedicalRecord> medicalRecordMap =
            Map.of("Jacob Boyd",MedicalRecord.builder().firstName("John").lastName("Boyd").birthdate("03/06/1984").allergies(List.of("nillacilan")).medications(List.of("aznol:350mg", "hydrapermazol:100mg")).build(),
                    "Tenley Boyd", MedicalRecord.builder().firstName("Tenley").lastName("Boyd").birthdate("03/06/2012").allergies(List.of("peanut")).medications(List.of()).build(),
                    "Ron Peters",MedicalRecord.builder().firstName("Ron").lastName("Peters").birthdate("04/06/1965").allergies(List.of()).medications(List.of()).build(),
                    "Lily Cooper",MedicalRecord.builder().firstName("Lily").lastName("Cooper").birthdate("03/06/1994").allergies(List.of("nillacilan")).medications(List.of()).build(),
                    "Brian Stelzer",MedicalRecord.builder().firstName("Brian").lastName("Stelzer").birthdate("12/06/1975").allergies(List.of("nillacilan")).medications(List.of("ibupurin:200mg", "hydrapermazol:400mg")).build(),
                    "Allisson Boyd",MedicalRecord.builder().firstName("Allison").lastName("Boyd").birthdate("03/15/1965").allergies(List.of("nillacilan")).medications(List.of("aznol:350mg")).build());


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

    private static final List<Person> getPersonByAddressList =  List.of(Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com").build(),
            Person.builder().firstName("Jacob").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6513").email("drk@email.com").build(),
            Person.builder().firstName("Tenley").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512").email("tenz@email.com").build());

    private static final List<FireStation> fireStationList = List.of(FireStation.builder().station("1").addresses(List.of("908 73rd St", "947 E. Rose Dr", "644 Gershwin Cir")).build(),
            FireStation.builder().station("2").addresses(List.of("29 15th St", "892 Downing Ct", "951 LoneTree Rd")).build(),
            FireStation.builder().station("3").addresses(List.of("834 Binoc Ave", "748 Townings Dr", "112 Steppes Pl", "1509 Culver St")).build(),
            FireStation.builder().station("4").addresses(List.of("112 Steppes Pl", "489 Manchester St")).build());


    @BeforeEach
    void setUp() {
    }
//    public ChildAndFamilyByAddressDto getChildAndFamilyByAddress(String address) {
//        Map<String, MedicalRecord> medicalRecordMap = fireStationService.stringMedicalRecordMap();
//        List<Person> getPersonByAddress = fireStationService.getPersonByAddress(address);
//        List<Person> other = new ArrayList<>();
//        List<ChildDto> child = new ArrayList<>();
//        ChildAndFamilyByAddressDto result;
//
//        for (Person persons : getPersonByAddress) {
//            String birthdate = medicalRecordMap.get(persons.getFirstName() + " " + persons.getLastName()).getBirthdate();
//            int age = fireStationService.ageCalculation(birthdate);
//            if (age <= 18) {
//                ChildDto childDto = new ChildDto(persons.getFirstName(), persons.getLastName(), age);
//                child.add(childDto);
//                List <Person> memberFamily = getPersonByAddress.stream().filter(e->e.getLastName().equals(persons.getLastName())).collect(Collectors.toList());
//                memberFamily.forEach(e->{
//                    if(!Objects.equals(e.getFirstName(), persons.getFirstName()))
//                        other.add(e);
//                });
//            }
//        }
//        result = new ChildAndFamilyByAddressDto(other,child);
//        return result;
    @Test
    void getChildAndFamilyByAddressTest() {
        when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordList);
        when(fireStationService.getPersonByAddress("1509 Culver St")).thenReturn(getPersonByAddressList);
        ChildAndFamilyByAddressDto childAndFamilyByAddressDto = personService.getChildAndFamilyByAddress("1509 Culver St");
        System.out.println(childAndFamilyByAddressDto);
        //TODO A FAIRE


    }
//
//    @Test
//    void getPersonPhoneByStationTest() throws IOException {
//        when(personRepository.findAll()).thenReturn(personList);
//        when(personRepository.findAll()).thenReturn(personList);
//        List<String> addresses = new ArrayList();
//        addresses.add("834 Binoc Ave");
//        addresses.add("748 Townings Dr");
//        addresses.add("112 Steppes Pl");
//        when(fireStationService.getAddressByStationNumber(3)).thenReturn(addresses);
//        Map<String, Set<String>> getPersonPhoneByStation = personService.getPersonPhoneByStation(3);
//        Set<String> phone = getPersonPhoneByStation.get("PhoneAlert");
//        assertEquals(phone.size(), 2);
//        //TODO verifier le contenu
//    }
//
//    @Test
//    void getPersonAndMedicalRecordPerAddressTest() throws IOException {
//        when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordList);
//        when(personRepository.findAll()).thenReturn(personList);
//        when(fireStationService.getStationByAddress("112 Steppes Pl")).thenReturn(List.of("3"));
//        Map<String, List<PersonWithMedicalRecordAndAgeDto>> getPersonAndMedicalRecordPerAddress = personService.getPersonAndMedicalRecordPerAddress("112 Steppes Pl");
//        List<PersonWithMedicalRecordAndAgeDto> person = getPersonAndMedicalRecordPerAddress.get("personWithMedicalRecordAndAge");
//        System.out.println(getPersonAndMedicalRecordPerAddress);
//        assertEquals(person.size(), 2);
//        assertEquals(person.get(0).getLastName(), "Peters");
//        assertEquals(person.get(1).getLastName(), "Boyd");
//    }
//
//    @Test
//    void getFamilyByStationTest() throws IOException {
//        List<String> addresses = new ArrayList();
//        addresses.add("834 Binoc Ave");
//        addresses.add("748 Townings Dr");
//        addresses.add("112 Steppes Pl");
//        when(fireStationService.getAddressByStationNumber(3)).thenReturn(addresses);
//        when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordList);
//        when(personRepository.findAll()).thenReturn(personList);
//        Map<String, List<FamilyByStationDto>> getFamilyByStation = personService.getFamilyByStation(3);
//        System.out.println(getFamilyByStation);
//        List<FamilyByStationDto> steppesPl = getFamilyByStation.get("112 Steppes Pl");
//        List<FamilyByStationDto> towningsDr = getFamilyByStation.get("748 Townings Dr");
//        List<FamilyByStationDto> binocAve = getFamilyByStation.get("834 Binoc Ave");
//        assertEquals(steppesPl.size(), 2);
//        assertEquals(steppesPl.get(0).getFirstName(), "Ron");
//        assertEquals(steppesPl.get(1).getFirstName(), "Allison");
//        assertEquals(towningsDr.size(), 0);
//        assertEquals(binocAve.size(), 0);
//    }
//
//    @Test
//    void getPersonEmailTest() {
//        when(personRepository.findAll()).thenReturn(personList);
//        Set<String> getPersonEmail = personService.getPersonEmail();
//        assertEquals(getPersonEmail.size(), 7);
//        assertTrue(getPersonEmail.contains("jaboyd@email.com"));
//        System.out.println(getPersonEmail);
//    }
//
//    @Test
//    void getPersonWithMedicalAndEmailTest() {
//        when(personRepository.findAll()).thenReturn(personList);
//        when(medicalRecordsRepository.findAll()).thenReturn(medicalRecordList);
//        Map<String, List<PersonWithMedicalAndEmailDto>> getPersonWithMedicalAndEmail = personService.getPersonWithMedicalAndEmail();
//        List<PersonWithMedicalAndEmailDto> person = getPersonWithMedicalAndEmail.get("personWithMedicalAndEmailList");
//        assertEquals(person.size(), 7);
//        assertEquals(person.get(0).getMedications(), List.of("aznol:350mg", "hydrapermazol:100mg"));
//        assertEquals(person.get(1).getMedications(), List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"));
//        assertEquals(person.get(6).getMedications(), List.of("aznol:350mg"));
//        System.out.println(getPersonWithMedicalAndEmail);
//    }

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
