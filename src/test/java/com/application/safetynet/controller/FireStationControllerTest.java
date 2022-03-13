package com.application.safetynet.controller;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.FireStationDto;
import com.application.safetynet.service.FireStationService;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private FireStation fireStation;

    @BeforeEach
    void setUp() {
        String station = "10";
        String address = "rue des tourbillons";
        ArrayList<String> addresses = new ArrayList<>();
        addresses.add(address);
        fireStation = new FireStation(station, addresses);
    }

    @Test
    void addFireStation() throws Exception {
        FireStationDto inputFireStation = new FireStationDto();
        inputFireStation.setStation("10");
        inputFireStation.setAddress("rue des tourbillons");
        Mockito.when(fireStationService.saveFireStation(fireStation)).thenReturn(fireStation);
        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputFireStation)))
                .andExpect(status().isOk());
    }

    @Test
    void getFireStations() throws Exception {
        List<FireStation> fireStationList = new ArrayList<>();
        fireStationList.add(fireStation);
        ArrayList<String> addresses = new ArrayList<>();
        addresses.add("rue des coucous");
        fireStationList.add(new FireStation("2", addresses));

        Mockito.when(fireStationService.getFireStations()).thenReturn(fireStationList);
        mockMvc.perform(get("/firestations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].station", is("2")));
    }

    @Test
    void updateFireStationWithExistingStation() throws Exception {
        FireStationDto inputFireStation = new FireStationDto();
        inputFireStation.setStation("8");
        inputFireStation.setAddress("rue des tourbillons");

        Mockito.when(fireStationService.saveFireStation(fireStation)).thenReturn(fireStation);
        Mockito.when(fireStationService.getFireStation("10")).thenReturn(java.util.Optional.ofNullable(fireStation));
        mockMvc.perform(put("/firestation/{station}", 10)
                        .content(new ObjectMapper().writeValueAsString(inputFireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value("8"))
                .andExpect(jsonPath("$.addresses").value("rue des tourbillons"));
    }

    @Test
    void updateFireStationWithNotExistingStation() throws Exception {
        FireStationDto inputFireStation = new FireStationDto();
        inputFireStation.setStation("8");
        inputFireStation.setAddress("rue des tourbillons");

        Mockito.when(fireStationService.getFireStation("10")).thenReturn(java.util.Optional.ofNullable(null));
        mockMvc.perform(put("/firestation/{station}", 10)
                        .content(new ObjectMapper().writeValueAsString(inputFireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFireStationWithRightData() throws Exception {
        FireStationDto inputFireStation = new FireStationDto();
        inputFireStation.setStation("8");
        inputFireStation.setAddress("rue des tourbillons");
        doNothing().when(fireStationService).deleteFireStations(isA(FireStationDto.class));
        fireStationService.deleteFireStations(inputFireStation);
        mockMvc.perform(delete("/firestation")
                        .content(new ObjectMapper().writeValueAsString(inputFireStation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFireStationWithWrongData() throws Exception {
        FireStationDto inputFireStation = new FireStationDto();
        inputFireStation.setStation("8");
        inputFireStation.setAddress("rue des tourbillons");
        doNothing().when(fireStationService).deleteFireStations(isA(FireStationDto.class));
        fireStationService.deleteFireStations(inputFireStation);
        mockMvc.perform(delete("/firestation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        assertThatThrownBy(() -> {
            throw new Exception("failed to delete the firestation. Exception error is: ");
        }).isInstanceOf(Exception.class);
    }
}