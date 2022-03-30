package com.application.safetynet.controller;


import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.ChildDto;
import com.application.safetynet.model.dto.PersonDto;
import com.application.safetynet.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    static final Map<String, Object> countAdultAndChild = Map.of("personByStationAddress",List.of(PersonDto.builder().firstName("Ron").lastName("Peters").address("112 Steppes Pl").phone("841-874-8888").build(),
                                                                                                      PersonDto.builder().firstName("Lily").lastName("Cooper").address("489 Manchester St").phone("841-874-9845").build()),"personUnderEighteen",0,"personOverEighteen",3);

    static final Map<String, Object> getChildAndFamilyByAddress = Map.of("other",List.of(Person.builder().firstName("Warren").lastName("Zemicks").address("892 Downing Ct").city("Culver").zip("97451").phone("841-874-7512").email("ward@email.com").build(),
            Person.builder().firstName("Sophia").lastName("Zemicks").address("892 Downing Ct").city("Culver").zip("97451").phone("841-874-9845").email("soph@email.com").build()),"child",List.of(ChildDto.builder().firstName("Zach").lastName("Zemicks").age(5).build()));

    static final Map <String, Set<String>> phone = Map.of("PhoneAlert",Set.of("841-874-7784","841-874-7462","841-874-6512","841-874-8547"));

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
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(personService.createPerson(person)).thenReturn(personList);
        mockMvc.perform(post("/person")
                        .content(new ObjectMapper().writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updatePerson() throws Exception {
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(personService.update(person)).thenReturn(personList);
        mockMvc.perform(put("/person")
                        .content(new ObjectMapper().writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAdultAndChildInStationTest() throws Exception {
        when(personService.countAdultAndChild(1)).thenReturn(countAdultAndChild);
        mockMvc.perform(get("/person/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personByStationAddress[0].firstName", is("Ron")))
                .andExpect(jsonPath("$.personUnderEighteen", is(0)))
                .andExpect(jsonPath("$.personOverEighteen", is(3)));
    }

    @Test
    void getChildByAddressWithFamilyTest() throws Exception {
        when(personService.getChildAndFamilyByAddress("892 Downing Ct")).thenReturn(getChildAndFamilyByAddress);
        mockMvc.perform(get("/person/family/{address}", "892 Downing Ct")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.other[0].firstName", is("Warren")))
                .andExpect(jsonPath("$.child[0].age", is(5)));
    }

//    //http://localhost:8080/phoneAlert?firestation=<firestation_number>
//    @GetMapping("/person/phone/station/{id}")
//    public Map<String, Set<String>> getPersonPhoneByStation(@PathVariable final int id) throws IOException {
//        logger.info("List of phone by station number {}",id);
//        return personService.getPersonPhoneByStation(id);
//    }

    @Test
    void getPersonPhoneByStationTest() throws Exception {
        when(personService.getPersonPhoneByStation(1)).thenReturn(phone);
        mockMvc.perform(get("/person/phone/station/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.PhoneAlert", hasSize(4)));

    }
}
