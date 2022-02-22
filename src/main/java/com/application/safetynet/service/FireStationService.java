package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.repository.FireStationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Data
@Service
public class FireStationService {

    @Autowired
    FireStationRepository fireStationRepository;

    public Optional<FireStation> getFireStation(final String id){ return fireStationRepository.findById(id);}

    public Iterable<FireStation> getFireStations(){return fireStationRepository.findAll();}

    public void deleteFireStations(final String id){
        fireStationRepository.deleteById(id);
    }

    public FireStation saveFireStation(FireStation fireStations){
        FireStation savedFireStation = fireStationRepository.save(fireStations);
        return savedFireStation;
    }
}
