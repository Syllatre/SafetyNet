package com.application.safetynet.repository;

import com.application.safetynet.model.FireStation;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface FireStationRepository  {
    void init() throws IOException;

    public List<FireStation> findAll();

    public Optional<FireStation> findByStation(String station);

    public void deleteByStation(String station);

    public FireStation save(FireStation fireStations);
}
