package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.FireStationDto;
import com.application.safetynet.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FireStationService {

    @Autowired
    FireStationRepository fireStationRepository;

    public Optional<FireStation> getFireStation(final String station) {
        return fireStationRepository.findByStation(station);
    }

    public List<FireStation> getFireStations() {
        return fireStationRepository.findAll();
    }

    public void deleteFireStations(FireStationDto id) {
        fireStationRepository.deleteFireStation(id);
    }

    public FireStation saveFireStation(FireStation fireStations) {
        FireStation savedFireStation = fireStationRepository.save(fireStations);
        return savedFireStation;
    }
}
