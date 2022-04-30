package com.application.safetynet.controller;

import com.application.safetynet.model.MedicalRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordsControllerIT {


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
    public void addMedicalRecordsIT() throws Exception {
        mockMvc.perform(post("/medicalrecords")
                        .content(asJsonString(new MedicalRecord("Aimen", "sasa", "08/30/1997", List.of(), List.of())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateMedicalRecordsIT() throws Exception {
        mockMvc.perform(put("/medicalrecords")
                        .content(asJsonString(new MedicalRecord("Zach", "Zemicks", "15/08/1965", List.of(), List.of())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void deleteMedicalRecordsIT() throws Exception {
        mockMvc.perform(delete("/medicalrecords")
                        .content(asJsonString(new MedicalRecord("Zach", "Zemicks", null, null, null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllIT() throws Exception {
        mockMvc.perform(get("/medicalrecords/findall")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
