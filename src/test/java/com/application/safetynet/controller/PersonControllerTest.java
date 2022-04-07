package com.application.safetynet.controller;


import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.ChildAlertDto;
import com.application.safetynet.model.dto.FamilyMember;
import com.application.safetynet.model.dto.PersonDto;
import com.application.safetynet.model.dto.PhoneAlert;
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

    private Person person;

    static final Map<String, Object> countAdultAndChild = Map.of("personByStationAddress", List.of(PersonDto.builder().firstName("Ron").lastName("Peters").address("112 Steppes Pl").phone("841-874-8888").build(),
            PersonDto.builder().firstName("Lily").lastName("Cooper").address("489 Manchester St").phone("841-874-9845").build()), "personUnderEighteen", 0, "personOverEighteen", 3);

    static final List<PhoneAlert> phone = List.of(PhoneAlert.builder().phone("841-874-7784").build(),
            PhoneAlert.builder().phone("841-874-7462").build(),
            PhoneAlert.builder().phone("841-874-6512").build(),
            PhoneAlert.builder().phone("841-874-8547").build());


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
        ChildAlertDto getChildAndFamilyByAddress = ChildAlertDto.builder().firstName("Zach").lastName("Zemicks").age(5).familyMembers(List.of(FamilyMember.builder().firstName("Warren").lastName("Zemicks").age(40).build(),
                FamilyMember.builder().firstName("Sophia").lastName("Zemicks").age(30).build())).build();
        when(personService.getChildAndFamilyByAddress("892 Downing Ct")).thenReturn(getChildAndFamilyByAddress);
        mockMvc.perform(get("/childAlert?address=892 Downing Ct", "892 Downing Ct")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Zach")))
                .andExpect(jsonPath("$.age", is(5)));
    }

    @Test
        // http://localhost:8080/phoneAlert?firestation=<firestation_number>
    void getPersonPhoneByStationTest() throws Exception {
        when(personService.getPersonPhoneByStation(1)).thenReturn(phone);
        mockMvc.perform(get("/phoneAlert?firestation=1", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.[3].phone", is("841-874-8547")));
    }

//    @Test
//    //http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
//    public void getPersonWithMedicalAndEmailTest() throws Exception {
//        mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd", "John", "Boyd")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    //http://localhost:8080/communityEmail?city=<city>
//    public void getPersonEmailTest() throws Exception {
//        mockMvc.perform(get("/communityEmail?city=Culver", "Culver")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }


}
