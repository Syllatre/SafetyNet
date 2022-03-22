package com.application.safetynet.repository;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.dto.FireStationDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface FireStationRepository {
    void init() throws IOException;

    public List<FireStation> findAll();

    public Optional<FireStation> findByStation(String station);

    public void deleteFireStation(FireStationDto id);

    public FireStation save(FireStation fireStations);
}
