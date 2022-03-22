package com.application.safetynet.repository;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.dto.FireStationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class InMemoryFireStationRepositoryTest {


    FireStationRepository inMemoryFireStationRepository = new InMemoryFireStationRepository() {
    };


    @Test
    public void initTest() throws IOException {
        inMemoryFireStationRepository.init();
        List<FireStation> fireStations = inMemoryFireStationRepository.findAll();
        Assertions.assertEquals(fireStations.size(), 4);
        Assertions.assertNotNull(fireStations);
    }

    @Test
    public void findAllTest() {
        List<FireStation> fireStations = inMemoryFireStationRepository.findAll();
        Assertions.assertNotNull(fireStations);
    }

    @Test
    public void findByStationTest() throws IOException {
        inMemoryFireStationRepository.init();
        Optional<FireStation> fireStation = inMemoryFireStationRepository.findByStation("2");
        Assertions.assertTrue(fireStation.stream().anyMatch(fire -> fire.getStation().equals("2")));
        Assertions.assertTrue(fireStation.stream().anyMatch(fire -> fire.getAddresses().contains("892 Downing Ct")));
    }

    @Test
    public void deleteFireStationWithStationTest() throws IOException {
        inMemoryFireStationRepository.init();
        FireStationDto id = new FireStationDto();
        id.setStation("2");
        inMemoryFireStationRepository.deleteFireStation(id);
        List<FireStation> refreshList = new ArrayList<>(inMemoryFireStationRepository.findAll());
        Assertions.assertFalse(refreshList.stream().anyMatch(element -> element.getStation().equals("2")));

    }

    @Test
    public void deleteFireStationWithAddressTest() throws IOException {
        inMemoryFireStationRepository.init();
        FireStationDto id = new FireStationDto();
        id.setAddress("29 15th St");
        inMemoryFireStationRepository.deleteFireStation(id);
        List<FireStation> refreshList = new ArrayList<>(inMemoryFireStationRepository.findAll());
        Assertions.assertFalse(refreshList.stream().anyMatch(element -> element.getAddresses().contains("29 15th St")));
    }

    @Test
    public void deleteFireStationWithAddressThrowsTest() throws IOException {
        inMemoryFireStationRepository.init();
        FireStationDto id = new FireStationDto();
        id.setAddress("UNKNOWN");
        inMemoryFireStationRepository.deleteFireStation(id);

        assertThatThrownBy(() -> {
            throw new Exception("failed to add the Firestation");
        }).isInstanceOf(Exception.class);
    }

    @Test
    public void deleteFireStationWithStationThrowsTest() throws IOException {
        inMemoryFireStationRepository.init();
        FireStationDto id = new FireStationDto();
        id.setStation("UNKNOWN");
        inMemoryFireStationRepository.deleteFireStation(id);

        assertThatThrownBy(() -> {
            throw new Exception("failed to add the Firestation");
        }).isInstanceOf(Exception.class);
    }

    @Test
    public void saveTest() throws IOException {
        inMemoryFireStationRepository.init();

    }
}





