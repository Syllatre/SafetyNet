package com.application.safetynet.controller;


import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.*;
import com.application.safetynet.repository.FireStationRepository;
import com.application.safetynet.repository.PersonRepository;
import com.application.safetynet.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    FireStationRepository fireStationRepository;

    private Person person;

    static final Map<String, Object> countAdultAndChild = Map.of("personByStationAddress", List.of(PersonDto.builder().firstName("Ron").lastName("Peters").address("112 Steppes Pl").phone("841-874-8888").build(),
            PersonDto.builder().firstName("Lily").lastName("Cooper").address("489 Manchester St").phone("841-874-9845").build()), "personUnderEighteen", 0, "personOverEighteen", 3);

    static final List<PhoneAlertDto> phone = List.of(PhoneAlertDto.builder().phone("841-874-7784").build(),
            PhoneAlertDto.builder().phone("841-874-7462").build(),
            PhoneAlertDto.builder().phone("841-874-6512").build(),
            PhoneAlertDto.builder().phone("841-874-8547").build());

    private static final List<Person> personList = List.of(Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com").build(),
            Person.builder().firstName("Jacob").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6513").email("drk@email.com").build(),
            Person.builder().firstName("Tenley").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512").email("tenz@email.com").build(),
            Person.builder().firstName("Ron").lastName("Peters").address("112 Steppes Pl").city("Culver").zip("97451").phone("841-874-8888").email("jpeter@email.com").build(),
            Person.builder().firstName("Lily").lastName("Cooper").address("489 Manchester St").city("Culver").zip("97451").phone("841-874-9845").email("lily@email.com").build(),
            Person.builder().firstName("Brian").lastName("Stelzer").address("892 Downing Ct").city("Culver").zip("97451").phone("841-874-7784").email("bstel@email.com").build(),
            Person.builder().firstName("Allison").lastName("Boyd").address("112 Steppes Pl").city("Culver").zip("97451").phone("841-874-9888").email("aly@imail.com").build());

    private static final List<FireStation> fireStationList = List.of(FireStation.builder().station("1").addresses(List.of("908 73rd St", "947 E. Rose Dr", "644 Gershwin Cir")).build(),
            FireStation.builder().station("2").addresses(List.of("29 15th St", "892 Downing Ct", "951 LoneTree Rd")).build(),
            FireStation.builder().station("3").addresses(List.of("834 Binoc Ave", "748 Townings Dr", "112 Steppes Pl", "1509 Culver St")).build(),
            FireStation.builder().station("4").addresses(List.of("112 Steppes Pl", "489 Manchester St")).build());

    private static final List<PersonWithMedicalAndEmailDto> personWithMedicalAndEmailDto = List.of(PersonWithMedicalAndEmailDto.builder().firstName("Jacob").lastName("Boyd").address("1509 Culver St").email("drk@email.com").age(58).medications(List.of("doliprane")).allergies(List.of()).build());

    private static final List<PersonEmailDto> email =
            List.of(PersonEmailDto.builder().email("jaboyd@email.com").build(),
                    PersonEmailDto.builder().email("drk@email.com").build(),
                    PersonEmailDto.builder().email("tenz@email.com").build());

    @BeforeEach
    void setUp() {
        String firstName = "Emilie";
        String lastName = "Petit";
        String address = "1 rue du roi";
        String city = "Nantes";
        String zip = "44000";
        String phone = "0213802975";
        String email = "emilie@gmail.com";
        person = new Person(firstName, lastName, address, city, zip, phone, email);
    }


    @Test
    void deletePerson() throws Exception {
        Person inputPerson = new Person();
        inputPerson.setFirstName("Emilie");
        inputPerson.setLastName("Petit");
        mockMvc.perform(delete("/person")
                        .content(new ObjectMapper().writeValueAsString(inputPerson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deletePersonBadRequest() throws Exception {
        Person inputPerson = new Person();
        inputPerson.setLastName("Petit");
        mockMvc.perform(delete("/person")
                        .content(new ObjectMapper().writeValueAsString(inputPerson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addPerson() throws Exception {
        when(personService.createPerson(person)).thenReturn(person);
        mockMvc.perform(post("/person")
                        .content(new ObjectMapper().writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updatePerson() throws Exception {
        when(personService.update(person)).thenReturn(person);
        mockMvc.perform(put("/person")
                        .content(new ObjectMapper().writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
        //http://localhost:8080/childAlert?address=<address>
    void getChildByAddressWithFamilyTest() throws Exception {
        ChildAlertDto getChildAndFamilyByAddress = ChildAlertDto.builder().firstName("Zach").lastName("Zemicks").age(5).familyMembers(List.of(FamilyMemberDto.builder().firstName("Warren").lastName("Zemicks").age(40).build(),
                FamilyMemberDto.builder().firstName("Sophia").lastName("Zemicks").age(30).build())).build();
        when(personService.getChildAndFamilyByAddress("892 Downing Ct")).thenReturn(getChildAndFamilyByAddress);
        when(personRepository.findAll()).thenReturn(personList);
        mockMvc.perform(get("/childAlert?address=892 Downing Ct", "892 Downing Ct")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Zach")))
                .andExpect(jsonPath("$.age", is(5)));
    }

    @Test
        //http://localhost:8080/childAlert?address=<address>
    void getChildByAddressWithFamilyNotFoundTest() throws Exception {
        ChildAlertDto getChildAndFamilyByAddress = ChildAlertDto.builder().firstName("Zach").lastName("Zemicks").age(5).familyMembers(List.of(FamilyMemberDto.builder().firstName("Warren").lastName("Zemicks").age(40).build(),
                FamilyMemberDto.builder().firstName("Sophia").lastName("Zemicks").age(30).build())).build();
        when(personService.getChildAndFamilyByAddress("892 Downing Ct")).thenReturn(getChildAndFamilyByAddress);
        mockMvc.perform(get("/childAlert?address=892 Downing Ct", "892 Downing Ct")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
        // http://localhost:8080/phoneAlert?firestation=<firestation_number>
    void getPersonPhoneByStationTest() throws Exception {
        when(personService.getPersonPhoneByStation(1)).thenReturn(phone);
        when(fireStationRepository.findAll()).thenReturn(fireStationList);
        mockMvc.perform(get("/phoneAlert?firestation=1", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.[3].phone", is("841-874-8547")));
    }

    @Test
        // http://localhost:8080/phoneAlert?firestation=<firestation_number>
    void getPersonPhoneByStationNotFoundTest() throws Exception {
        when(personService.getPersonPhoneByStation(1)).thenReturn(phone);
        mockMvc.perform(get("/phoneAlert?firestation=1", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    //http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    public void getPersonWithMedicalAndEmailNotFoundTest() throws Exception {

        when(personService.getPersonWithMedicalAndEmail("John","Boyd")).thenReturn(personWithMedicalAndEmailDto);
        mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd", "John", "Boyd")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    //http://localhost:8080/communityEmail?city=<city>
    public void getPersonEmailTest() throws Exception {
        when(personRepository.findAll()).thenReturn(personList);
        when(personService.getPersonEmail("Culver")).thenReturn(email);
        mockMvc.perform(get("/communityEmail?city=Culver", "Culver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    //http://localhost:8080/communityEmail?city=<city>
    public void getPersonEmailNotFoundTest() throws Exception {
        when(personService.getPersonEmail("Culver")).thenReturn(email);
        mockMvc.perform(get("/communityEmail?city=Culver", "Culver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    //http://localhost:8080/person/findall
    @Test
    public void findAllIT() throws Exception {
        when(personService.findAll()).thenReturn(personList);
        mockMvc.perform(get("/person/findall")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(7)));
    }
}
