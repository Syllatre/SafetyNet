package com.application.safetynet.repository;


import com.application.safetynet.model.FireStation;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;

@Repository
public interface FireStationRepository  {
    void init() throws IOException;

    public Iterable<FireStation> findAll();

    public Optional<FireStation> findById(String id);

    public void deleteById(String id);

    public FireStation save(FireStation fireStations);
}
