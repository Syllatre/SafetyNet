package com.application.safetynet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.FireStationDao;
import com.application.safetynet.repository.FireStationRepository;
import com.application.safetynet.repository.InMemoryFireStationRepository;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;
import java.util.stream.LongStream;


@SpringBootTest
public class InMemoryFireStationRepositoryTest {

    @Autowired
    FireStationRepository inMemoryFireStationRepository;

    @Before


    @Test
    public void initTest() throws IOException {
        inMemoryFireStationRepository.init();
        List<FireStation> fireStations = inMemoryFireStationRepository.findAll();
        Assertions.assertEquals(fireStations.size(),4);
        Assertions.assertNotNull(fireStations);

    }
    @Test
    public void findAllTest(){
        List<FireStation> fireStations = inMemoryFireStationRepository.findAll();
        Assertions.assertNotNull(fireStations);
    }

    @Test
    public void findByStationTest() throws IOException {
        inMemoryFireStationRepository.init();
       Optional<FireStation> fireStation = inMemoryFireStationRepository.findByStation("2");
       Assertions.assertTrue(fireStation.stream().anyMatch(fire->fire.getStation().equals("2")));
        Assertions.assertTrue(fireStation.stream().anyMatch(fire->fire.getAddresses().contains("892 Downing Ct")));
    }

    @Test
    public void deleteFireStationWithStationTest() throws IOException{
        inMemoryFireStationRepository.init();
        FireStationDao id = new FireStationDao();
        id.setStation("2");
        List<FireStation> fireStations = inMemoryFireStationRepository.deleteFireStation(id);
        Assertions.assertFalse(fireStations.stream().anyMatch(element->element.getStation().equals("2")));

    }
    @Test
    public void deleteFireStationWithAddressTest() throws IOException{
        inMemoryFireStationRepository.init();
        FireStationDao id = new FireStationDao();
        id.setAddress("29 15th St");
        List<FireStation> fireStations = inMemoryFireStationRepository.deleteFireStation(id);
        Assertions.assertFalse(fireStations.stream().anyMatch(element->element.getAddresses().contains("29 15th St")));
    }

    @Test
    public void deleteFireStationWithAddressThrowsTest() throws IOException{
        inMemoryFireStationRepository.init();
        FireStationDao id = new FireStationDao();
        id.setAddress("UNKNOWN");
        List<FireStation> fireStations = inMemoryFireStationRepository.deleteFireStation(id);

        assertThatThrownBy(() -> {
            throw new Exception("failed to add the Firestation");
        }).isInstanceOf(Exception.class);
    }

    @Test
    public void deleteFireStationWithStationThrowsTest() throws IOException{
        inMemoryFireStationRepository.init();
        FireStationDao id = new FireStationDao();
        id.setStation("UNKNOWN");
        List<FireStation> fireStations = inMemoryFireStationRepository.deleteFireStation(id);

        assertThatThrownBy(() -> {
            throw new Exception("failed to add the Firestation");
        }).isInstanceOf(Exception.class);
    }
    @Test
    public void saveTest() throws IOException{
        inMemoryFireStationRepository.init();

    }
}





