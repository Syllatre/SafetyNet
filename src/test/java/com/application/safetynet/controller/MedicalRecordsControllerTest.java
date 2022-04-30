package com.application.safetynet.controller;

import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.service.MedicalRecordsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    private static final List<MedicalRecord> medicalRecordList = List.of(MedicalRecord.builder().firstName("John").lastName("Boyd").birthdate("03/06/1984").allergies(List.of("nillacilan")).medications(List.of("aznol:350mg", "hydrapermazol:100mg")).build(),
            MedicalRecord.builder().firstName("Jacob").lastName("Boyd").birthdate("03/06/1989").allergies(List.of()).medications(List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")).build(),
            MedicalRecord.builder().firstName("Tenley").lastName("Boyd").birthdate("03/06/2012").allergies(List.of("peanut")).medications(List.of()).build(),
            MedicalRecord.builder().firstName("Ron").lastName("Peters").birthdate("04/06/1965").allergies(List.of()).medications(List.of()).build(),
            MedicalRecord.builder().firstName("Lily").lastName("Cooper").birthdate("03/06/1994").allergies(List.of("nillacilan")).medications(List.of()).build(),
            MedicalRecord.builder().firstName("Brian").lastName("Stelzer").birthdate("12/06/1975").allergies(List.of("nillacilan")).medications(List.of("ibupurin:200mg", "hydrapermazol:400mg")).build(),
            MedicalRecord.builder().firstName("Allison").lastName("Boyd").birthdate("03/15/1965").allergies(List.of("nillacilan")).medications(List.of("aznol:350mg")).build());


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
    void deletePersonBadRequest() throws Exception {
        MedicalRecord inputMedicalRecords = new MedicalRecord();
        inputMedicalRecords.setLastName("Petit");
        List<MedicalRecord> medicalRecordsList = new ArrayList<>();
        medicalRecordsList.add(medicalRecord);
        mockMvc.perform(delete("/medicalrecords")
                        .content(new ObjectMapper().writeValueAsString(inputMedicalRecords))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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

    }

    //http://localhost:8080/medicalrecords/findall
    @Test
    public void findAllIT() throws Exception {
        when(medicalRecordsService.findAll()).thenReturn(medicalRecordList);
        mockMvc.perform(get("/medicalrecords/findall")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(7)));
    }
}
