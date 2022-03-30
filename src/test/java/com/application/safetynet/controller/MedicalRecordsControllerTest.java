package com.application.safetynet.controller;

import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.service.MedicalRecordsService;
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

@WebMvcTest(MedicalRecordsController.class)
public class MedicalRecordsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MedicalRecordsService medicalRecordsService;

    private MedicalRecord medicalRecord;

    @BeforeEach
    void setUp() {
        String firstName = "Emilie";
        String lastName = "Petit";
        String birthdate = "20/03/99";
        List<String> medications = new ArrayList<>();
        medications.add("doliprane");
        List<String> allergies = new ArrayList<>();
        medications.add("cat");
        medicalRecord = new MedicalRecord(firstName, lastName, birthdate, medications, allergies);
    }


    @Test
    void deletePerson() throws Exception {
        MedicalRecord inputMedicalRecords = new MedicalRecord();
        inputMedicalRecords.setFirstName("Emilie");
        inputMedicalRecords.setLastName("Petit");
        List<MedicalRecord> medicalRecordsList = new ArrayList<>();
        medicalRecordsList.add(medicalRecord);
        mockMvc.perform(delete("/medicalrecords")
                        .content(new ObjectMapper().writeValueAsString(inputMedicalRecords))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addMedicalRecords() throws Exception {
        when(medicalRecordsService.createMedicalRecords(medicalRecord)).thenReturn(medicalRecord);
        mockMvc.perform(post("/medicalrecords")
                        .content(new ObjectMapper().writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updatePerson() throws Exception {
        when(medicalRecordsService.update(medicalRecord)).thenReturn(medicalRecord);
        mockMvc.perform(put("/medicalrecords")
                        .content(new ObjectMapper().writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.firstName").value("Emilie"))
//                .andExpect(jsonPath("$.city").value("Nantes"));
    }
}
