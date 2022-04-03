package com.application.safetynet.controller;


import com.application.safetynet.model.dto.FireStationDto;
import com.application.safetynet.repository.InMemoryFireStationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    public static String asJsonString(final FireStationDto fireStationDto) {
        try {
            return new ObjectMapper().writeValueAsString(fireStationDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void addFireStationIT() throws Exception {
        FireStationDto fireStation = new FireStationDto("10", "address");
        mockMvc.perform(post("/firestation")
                        .content(asJsonString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    public void updateFireStationIT() throws Exception {
        mockMvc.perform(put("/firestation/{station}", 2)
                        .content(asJsonString(new FireStationDto("4", null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value("4"));

    }

    @Test
    public void deleteFireStationIT() throws Exception {
        mockMvc.perform(delete("/firestation/")
                        .content(asJsonString(new FireStationDto("4", null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getAdultAndChildInStationIT() throws Exception {
        mockMvc.perform(get("/firestation?stationNumber=2", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
