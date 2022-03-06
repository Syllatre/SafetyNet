package com.application.safetynet;

import com.application.safetynet.controller.FireStationController;
import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.FireStationDao;
import com.application.safetynet.service.FireStationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.BDDAssertions.and;
import static org.hamcrest.CoreMatchers.is;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FireStationControllerTest {



    @Autowired
    public MockMvc mockMvc;


    @Test
    public void getFireStationsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/firestations")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].station",is("1")));
    }
    @Test
    public void createFireStationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/firestation")
                .content(asJsonString(new FireStationDao("10","bonjour")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].station",is("1")));
    }
    public static String asJsonString(final Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @Test
    public void saveFireStationTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/firestation/{station}", 2)
                        .content(asJsonString(new FireStationDao("3", null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value("3"));

    }
}
