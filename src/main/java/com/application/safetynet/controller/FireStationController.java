package com.application.safetynet.controller;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.dto.FireStationDto;
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
    private static final Logger logger = LoggerFactory.getLogger(FireStationController.class);

    @Autowired
    private FireStationService fireStationService;

    @PostMapping("/firestation")
    public FireStation addFireStation(@RequestBody FireStationDto fireStationDto) {
        logger.info("Creating firestation {}",fireStationDto);
        List<String> address = new ArrayList<>();

        address.add(fireStationDto.getAddress());
        FireStation convertFireStationDto = new FireStation(fireStationDto.getStation(), address);
        return fireStationService.saveFireStation(convertFireStationDto);
    }


    @PutMapping("/firestation/{station}")
    public FireStation updateFireStation(@PathVariable("station") final String station, @RequestBody FireStationDto fireStationDto) {
        logger.info("Updating firestation {}",fireStationDto);
        Optional<FireStation> f = fireStationService.getFireStation(station);
        if (f.isPresent()) {
            FireStation currentFireStation = f.get();
            FireStationDto convertCurrentFireStation = new FireStationDto(currentFireStation.getStation(), null);
            List<String> address = new ArrayList<>();
            address.add(fireStationDto.getAddress());
            FireStation convertFireStationDto = new FireStation(fireStationDto.getStation(), address);

            String station1 = convertFireStationDto.getStation();
            if (station != null) {
                deleteFireStation(convertCurrentFireStation);
                currentFireStation.setStation(station1);
            }
            fireStationService.saveFireStation(currentFireStation);

            return currentFireStation;
        } else {
            return null;
        }
    }

    @DeleteMapping("/firestation")
    public void deleteFireStation(@RequestBody FireStationDto id) {
        logger.error("Deleting firestation {}", id);
        fireStationService.deleteFireStations(id);
    }
}
