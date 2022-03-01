package com.application.safetynet.controller;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @PostMapping("/firestation")
    public FireStation createFireStation(@RequestBody FireStation fireStation){
        return fireStationService.saveFireStation(fireStation);
    }

    @GetMapping("/firestation/{station}")
    public FireStation getFireStation (@PathVariable("station")final String station){
        Optional<FireStation> fireStations =fireStationService.getFireStation(station);
        if(fireStations.isPresent()){
            return fireStations.get();
        }
        else{
            return  null;
        }
    }
    @GetMapping("/firestations")
    public List<FireStation> getFireStations(){
        return fireStationService.getFireStations();
    }

    @PutMapping("/firestation/{station}")
    public FireStation saveFireStation (@PathVariable("station") final String station, @RequestBody FireStation fireStation){
        Optional<FireStation> f =fireStationService.getFireStation(station);
        if (f.isPresent()){
            FireStation currentFireStation = f.get();

            String station1 = fireStation.getStation();
            if(station != null){
                deleteFireStation(currentFireStation.getStation());
                currentFireStation.setStation(station1);

            }
            fireStationService.saveFireStation(currentFireStation);
            return currentFireStation;
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/firestation/{station}")
    public void deleteFireStation(@PathVariable("station")final String station){
        fireStationService.deleteFireStations(station);
    }
}
