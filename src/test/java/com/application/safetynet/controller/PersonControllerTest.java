package com.application.safetynet.controller;


import com.application.safetynet.model.Person;
import com.application.safetynet.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    private Person person;

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
//                .andExpect(jsonPath("$.firstName").value("Emilie"))
//                .andExpect(jsonPath("$.city").value("Nantes"));
    }
}
