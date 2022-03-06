package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.FireStationDao;
import com.application.safetynet.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class FireStationService {

    @Autowired
    FireStationRepository fireStationRepository;

    public Optional<FireStation> getFireStation(final String station){ return fireStationRepository.findByStation(station);}

    public List<FireStation> getFireStations(){
        return fireStationRepository.findAll();
    }

    public void deleteFireStations(FireStationDao id){
        fireStationRepository.deleteFireStation(id);
    }

    public FireStation saveFireStation(FireStation fireStations){
        FireStation savedFireStation = fireStationRepository.save(fireStations);
        return savedFireStation;
    }
}
