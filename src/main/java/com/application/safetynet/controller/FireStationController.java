package com.application.safetynet.controller;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.FireStationDao;
import com.application.safetynet.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FireStationController {
    private static Logger logger = LoggerFactory.getLogger(FireStationController.class);

    @Autowired
    private FireStationService fireStationService;

    @PostMapping("/firestation")
    public FireStation createFireStation(@RequestBody FireStationDao fireStationDao){
        List<String> address = new ArrayList<>();
        address.add(fireStationDao.getAddress());
        FireStation convertFireStationDao = new FireStation(fireStationDao.getStation(),address);
        return fireStationService.saveFireStation(convertFireStationDao);
    }

    @GetMapping("/firestations")
    public List<FireStation> getFireStations(){
        return fireStationService.getFireStations();
    }

    @PutMapping("/firestation/{station}")
    public FireStation saveFireStation (@PathVariable("station") final String station, @RequestBody FireStationDao fireStationDao){
        logger.info("http://localhost:8080/firestation/"+station);
        logger.info("station: " + station);
        Optional<FireStation> f =fireStationService.getFireStation(station);
        if (f.isPresent()){
            FireStation currentFireStation = f.get();
            FireStationDao convertCurrentFireStation = new FireStationDao(currentFireStation.getStation(), null);
            List<String> address = new ArrayList<>();
            address.add(fireStationDao.getAddress());
            FireStation convertFireStationDao = new FireStation(fireStationDao.getStation(),address);

            String station1 = convertFireStationDao.getStation();
            if(station != null){
                deleteFireStation(convertCurrentFireStation);
                currentFireStation.setStation(station1);
            }
            fireStationService.saveFireStation(currentFireStation);

            return currentFireStation;
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/firestation/")
    public void deleteFireStation(@RequestBody FireStationDao id){
        logger.info("http://localhost:8080/firestation");
        logger.info("body: " + id);
        try {
            fireStationService.deleteFireStations(id);
        }catch (Exception e){
            logger.error("failed to delete the firestation. Exception error is: " + e);
        }
    }
}
