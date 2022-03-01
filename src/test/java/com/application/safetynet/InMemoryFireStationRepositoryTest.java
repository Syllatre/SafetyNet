package com.application.safetynet;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.repository.InMemoryFireStationRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class InMemoryFireStationRepositoryTest {

    @Autowired
    InMemoryFireStationRepository inMemoryFireStationRepository;

    Map<String, FireStation> stringFireStationMap = new HashMap<>();
    @Test
    public void initTest() throws IOException {

    }
}





