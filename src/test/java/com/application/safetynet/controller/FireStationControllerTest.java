package com.application.safetynet.controller;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.dto.CountChildAndAdult;
import com.application.safetynet.model.dto.FireStationDto;
import com.application.safetynet.model.dto.PersonDto;
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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

        Mockito.when(fireStationService.getFireStation("10")).thenReturn(java.util.Optional.empty());
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

    @Test
    void getAdultAndChildInStationTest() throws Exception {
        CountChildAndAdult countChildAndAdult;
        List<PersonDto> personDtoList = new ArrayList<>();
        PersonDto personDto1 = new PersonDto("Zach","Zemicks","892 Downing Ct","841-874-7512");
        PersonDto personDto2 = new PersonDto("Warren","Zemicks","892 Downing Ct","841-874-7512");
        PersonDto personDto3 = new PersonDto("Sophia","Zemicks","892 Downing Ct","841-874-7878");
        PersonDto personDto4 = new PersonDto("Eric","Cadigan","951 LoneTree Rd","841-874-7458");
        PersonDto personDto5 = new PersonDto("Jonanathan","Marrack","29 15th St","841-874-6513");
        personDtoList.add(personDto1);
        personDtoList.add(personDto2);
        personDtoList.add(personDto3);
        personDtoList.add(personDto4);
        personDtoList.add(personDto5);
        countChildAndAdult = new CountChildAndAdult(personDtoList,1,4);
        when(fireStationService.countAdultAndChild(2)).thenReturn(countChildAndAdult);
        mockMvc.perform(get("/firestation?stationNumber=2", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}