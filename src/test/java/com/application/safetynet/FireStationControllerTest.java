package com.application.safetynet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FireStationControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void testGetFireStations() throws Exception {
        mockMvc.perform(get("/firestations")).andExpect(status().isOk());
    }
}
