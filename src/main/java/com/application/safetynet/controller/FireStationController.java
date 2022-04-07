package com.application.safetynet.controller;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.dto.CountChildAndAdult;
import com.application.safetynet.model.dto.FireStationDto;
import com.application.safetynet.model.dto.FloodDto;
import com.application.safetynet.model.dto.PersonWithMedicalRecordAndAgeDto;
import com.application.safetynet.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FireStationController {
    private static final Logger logger = LoggerFactory.getLogger(FireStationController.class);

    @Autowired
    private FireStationService fireStationService;

    /**
     * Get all persons attached to the station with a count of people under 18 and under 18
     *
     * @param id firestation number
     * @return List of persons with count of people under 18 and under 18
     */
    //http://localhost:8080/firestation?stationNumber=<station_number>
    @GetMapping("/firestation")
    public CountChildAndAdult getAdultAndChildInStation(@RequestParam(name = "stationNumber") int id) throws IOException {
        logger.info("List of child and adult in station{}", id);
        return fireStationService.countAdultAndChild(id);
    }

    /**
     * Get all persons attached to the address station with a medical records and age
     *
     * @param address firestation address
     * @return List of persons with medical records and age
     */
    //http://localhost:8080/fire?address=<address>
    @GetMapping("fire")
    public List<PersonWithMedicalRecordAndAgeDto> getPersonAndMedicalRecordPerAddress(@RequestParam(name = "address") final String address) {
        logger.info("List of medical records by address {}", address);
        return fireStationService.getPersonAndMedicalRecordPerAddress(address);
    }

    /**
     * Get all persons attached to the address station with a medical records and age
     *
     * @param id id is list of firestation number
     * @return List of family by station number
     */
    //http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    @GetMapping("flood/stations")
    public List<FloodDto> getFamilyByStation(@RequestParam(name = "stations") final List<Integer> id) throws IOException {
        logger.info("List of family by station number {}", id);
        return fireStationService.getFamilyByStation(id);
    }

    @PostMapping("/firestation")
    public FireStation addFireStation(@RequestBody FireStationDto fireStationDto) {
        logger.info("Creating firestation {}", fireStationDto);
        List<String> address = new ArrayList<>();

        address.add(fireStationDto.getAddress());
        FireStation convertFireStationDto = new FireStation(fireStationDto.getStation(), address);
        return fireStationService.saveFireStation(convertFireStationDto);
    }


    @PutMapping("/firestation/{station}")
    public FireStation updateFireStation(@PathVariable("station") final String station, @RequestBody FireStationDto fireStationDto) {
        logger.info("Updating firestation {}", fireStationDto);
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
