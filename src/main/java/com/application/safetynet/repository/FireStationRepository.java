package com.application.safetynet.repository;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.dto.FireStationDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface FireStationRepository {
    void init() throws IOException;

    List<FireStation> findAll();


    Optional<FireStation> findByStation(String station);

    void deleteFireStation(FireStationDto id);

    FireStation save(FireStation fireStations);
}
