package com.application.safetynet.controller;


import com.application.safetynet.model.FireStationDao;
import com.application.safetynet.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

    @Autowired
    public MockMvc mockMvc;


    public static String asJsonString(final Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getFireStationsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/persons")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName",is("Zach")));
    }

    @Test
    public void addPersonTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/person")
                        .content(asJsonString(new Person("Aimen","sasa","15 rue des oiseaux", "paris","75013","0145284578","aimen@gmail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Aimen"));
    }

    @Test
    public void updateTest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/person/{station}", 2)
                        .content(asJsonString(new FireStationDao("3", null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value("3"));

    }

//    @Test
//    public void deleteEmployeeAPI() throws Exception
//    {
//        mockMvc.perform( MockMvcRequestBuilders.delete("/person")
//                .content(asJsonString(new Person("Zach", "Zemicks", null, null,null,null,null)))
//                .andExpect(status().isAccepted()));
//    }
}
