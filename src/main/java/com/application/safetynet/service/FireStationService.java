package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.dto.FireStationDto;
import com.application.safetynet.repository.FireStationRepository;
import com.application.safetynet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FireStationService {

    @Autowired
    FireStationRepository fireStationRepository;

    @Autowired
    PersonRepository personRepository;



    public Optional<FireStation> getFireStation(final String station) {
        return fireStationRepository.findByStation(station);
    }


    public void deleteFireStations(FireStationDto id) {
        fireStationRepository.deleteFireStation(id);
    }

    public FireStation saveFireStation(FireStation fireStations) {
        FireStation savedFireStation = fireStationRepository.save(fireStations);
        return savedFireStation;
    }

    public List<String> getAddressByStationNumber(int stationNumber) throws IOException {
        List<FireStation> fireStationsList = fireStationRepository.findAll();
        return fireStationsList
                .stream().
                filter(station -> Integer.parseInt(station.getStation()) == stationNumber)
                .flatMap(station-> station.getAddresses().stream())
                .collect(Collectors.toList());
    }

    public String getStationByAddress (String address){
        List<FireStation> fireStationsList = fireStationRepository.findAll();
        String station = null;
        for (FireStation fireStation : fireStationsList){
            if (fireStation.getAddresses().contains(address)){
                 station = fireStation.getStation();
            }
        }
        return station;
    }
}
