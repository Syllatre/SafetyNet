package com.application.safetynet.controller;


import com.application.safetynet.model.FireStationDto;
import com.application.safetynet.repository.InMemoryFireStationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FireStationControllerIT {


    @Autowired
    InMemoryFireStationRepository inMemoryFireStationRepository;

    @Autowired
    public MockMvc mockMvc;

    @Before
    public void setup() {
        try {
            inMemoryFireStationRepository.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getFireStationsIT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/firestations")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].station", is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)));
    }

    @Test
    public void addFireStationIT() throws Exception {
        FireStationDto fireStation = new FireStationDto("10", "address");
        mockMvc.perform(post("/firestation")
                        .content(new ObjectMapper().writeValueAsString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    public static String asJsonString(final FireStationDto fireStationDto) {
        try {
            return new ObjectMapper().writeValueAsString(fireStationDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateFireStationIT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/firestation/{station}", 2)
                        .content(asJsonString(new FireStationDto("4", null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value("4"));

    }

    @Test
    public void deleteFireStationIT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/firestation/")
                        .content(asJsonString(new FireStationDto("4", null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
