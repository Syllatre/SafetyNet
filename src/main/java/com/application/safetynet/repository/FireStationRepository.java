package com.application.safetynet.repository;

import com.application.safetynet.model.FireStation;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface FireStationRepository  {
    void init() throws IOException;

    public List<FireStation> findAll();

    public Optional<FireStation> findById(String id);

    public void deleteById(String id);

    public FireStation save(FireStation fireStations);
}
