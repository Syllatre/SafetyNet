package com.application.safetynet.controller;

import com.application.safetynet.model.MedicalRecords;
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

    private MedicalRecords medicalRecords;

    @BeforeEach
    void setUp() {
        String firstName = "Emilie";
        String lastName = "Petit";
        String birthdate = "20/03/99";
        String medications = "[doliprane]";
        String allergies = "[]";
        medicalRecords = new MedicalRecords(firstName, lastName, birthdate, medications, allergies);
    }

    @Test
    void getMedicalRecordsTest() throws Exception {
        String firstName = "Stephane";
        String lastName = "Broni";
        String birthdate = "15/02/89";
        String medications = "[doliprane]";
        String allergies = "[]";

        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        medicalRecordsList.add(medicalRecords);
        medicalRecordsList.add(new MedicalRecords(firstName, lastName, birthdate, medications, allergies));

        Mockito.when(medicalRecordsService.getMedicalRecords()).thenReturn(medicalRecordsList);
        mockMvc.perform(get("/medicalrecords")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].birthdate", is("15/02/89")))
                .andExpect(jsonPath("$[0].lastName", is("Petit")));
    }

    @Test
    void deletePerson() throws Exception {
        MedicalRecords inputMedicalRecords = new MedicalRecords();
        inputMedicalRecords.setFirstName("Emilie");
        inputMedicalRecords.setLastName("Petit");
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        medicalRecordsList.add(medicalRecords);
        when(medicalRecordsService.delete(inputMedicalRecords)).thenReturn(medicalRecordsList);
        mockMvc.perform(delete("/medicalrecords")
                        .content(new ObjectMapper().writeValueAsString(inputMedicalRecords))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addMedicalRecords() throws Exception {
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        medicalRecordsList.add(medicalRecords);
        when(medicalRecordsService.createMedicalRecords(medicalRecords)).thenReturn(medicalRecordsList);
        mockMvc.perform(post("/medicalrecords")
                        .content(new ObjectMapper().writeValueAsString(medicalRecords))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updatePerson() throws Exception {
        List<MedicalRecords> medicalRecordsList = new ArrayList<>();
        medicalRecordsList.add(medicalRecords);
        when(medicalRecordsService.update(medicalRecords)).thenReturn(medicalRecordsList);
        mockMvc.perform(put("/medicalrecords")
                        .content(new ObjectMapper().writeValueAsString(medicalRecords))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.firstName").value("Emilie"))
//                .andExpect(jsonPath("$.city").value("Nantes"));
    }
}
