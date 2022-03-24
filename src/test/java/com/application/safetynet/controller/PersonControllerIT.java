package com.application.safetynet.controller;


import com.application.safetynet.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
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


    @Test
    public void getAdultAndChildInStationIT() throws Exception {
        mockMvc.perform(get("/person/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personByStationAddress[0].firstName", is("Shawna")))
                .andExpect(jsonPath("$.personUnderEighteen", is(1)))
                .andExpect(jsonPath("$.personOverEighteen", is(5)));
    }


    @Test
    public void getChildByAddressWithFamilyIT() throws Exception {
        mockMvc.perform(get("/person/family/{address}", "947 E. Rose Dr")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.other[0].firstName", is("Shawna")))
                .andExpect(jsonPath("$.other", hasSize(2)))
                .andExpect(jsonPath("$.child[0].age", is(8)));
    }

    @Test
    public void getPersonPhoneByStationIT() throws Exception {
        mockMvc.perform(get("/person/phone/station/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.PhoneAlert", hasSize(6)));
    }

    @Test
    public void getPersonAndMedicalRecordPerAddressIT() throws Exception {
        mockMvc.perform(get("/person/medicalrecord/{address}", "947 E. Rose Dr")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personWithMedicalRecordAndAge[0].station", is("1")))
                .andExpect(jsonPath("$.personWithMedicalRecordAndAge[0].age", is(41)))
                .andExpect(jsonPath("$.personWithMedicalRecordAndAge[1].medications[0]", is("noxidian:100mg")))
                .andExpect(jsonPath("$.personWithMedicalRecordAndAge", hasSize(3)));

    }

    @Test
    public void getPersonEmailIT() throws Exception {
        mockMvc.perform(get("/person/email")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(23)));
    }


    @Test
    public void addPersonIT() throws Exception {
        mockMvc.perform(post("/person")
                        .content(asJsonString(new Person("Aimen", "sasa", "15 rue des oiseaux", "paris", "75013", "0145284578", "aimen@gmail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePersonIT() throws Exception {
        mockMvc.perform(put("/person")
                        .content(asJsonString(new Person("Zach", "Zemicks", "15 rue des oiseaux", "paris", "75013", "0145284578", "aimen@gmail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void deletePersonIT() throws Exception {
        mockMvc.perform(delete("/person")
                        .content(asJsonString(new Person("Zach", "Zemicks", null, null, null, null, null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
}
