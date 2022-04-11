package com.application.safetynet.controller;


import com.application.safetynet.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

    @Autowired
    public MockMvc mockMvc;


    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Order(1)
    @Test
    //http://localhost:8080/childAlert?address=<address>
    public void getChildByAddressWithFamilyIT() throws Exception {
        mockMvc.perform(get("/childAlert?address=947 E. Rose Dr", "947 E. Rose Dr")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Kendrik")))
                .andExpect(jsonPath("$.familyMembers", hasSize(2)));
    }

    @Order(2)
    @Test
    //http://localhost:8080/phoneAlert?firestation=<firestation_number>
    public void getPersonPhoneByStationIT() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestation=1", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Order(3)
    @Test
    //http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    public void getPersonWithMedicalAndEmailIT() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd", "John", "Boyd")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(4)
    @Test
    //http://localhost:8080/communityEmail?city=<city>
    public void getPersonEmailIT() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver", "Culver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(5)
    @Test
    public void addPersonIT() throws Exception {
        mockMvc.perform(post("/person")
                        .content(asJsonString(new Person("Aimen", "sasa", "15 rue des oiseaux", "paris", "75013", "0145284578", "aimen@gmail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(6)
    @Test
    public void updatePersonIT() throws Exception {
        mockMvc.perform(put("/person")
                        .content(asJsonString(new Person("Zach", "Zemicks", "15 rue des oiseaux", "paris", "75013", "0145284578", "aimen@gmail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Order(7)
    @Test
    public void deletePersonIT() throws Exception {
        mockMvc.perform(delete("/person")
                        .content(asJsonString(new Person("Zach", "Zemicks", null, null, null, null, null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
