//package com.application.safetynet.controller;
//
//import com.application.safetynet.model.FireStation;
//import com.application.safetynet.service.FireStationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//public class FireStationController {
//
//    @Autowired
//    private FireStationService fireStationService;
//
//    @PostMapping("/firestation")
//    public FireStation createFireStation(@RequestBody FireStation fireStation){
//        return fireStationService.saveFireStation(fireStation);
//    }
//
//    @GetMapping("/firestation/{id}")
//    public FireStation getFireStation (@PathVariable("id")final String id){
//        Optional<FireStation> fireStations =fireStationService.getFireStation(id);
//        if(fireStations.isPresent()){
//            return fireStations.get();
//        }
//        else{
//            return  null;
//        }
//    }
//    @GetMapping("/firestations")
//    public Iterable<FireStation> getFireStations(){
//        return fireStationService.getFireStations();
//    }
//
//    @PutMapping("/firestation/{id}")
//    public FireStation updateFireStation (@PathVariable("id") final String id, @RequestBody FireStation fireStation){
//        Optional<FireStation> f =fireStationService.getFireStation(id);
//        if (f.isPresent()){
//            FireStation currentFireStation = f.get();
//
//            String address = fireStation.getAddress();
//            if(address != null){
//                currentFireStation.setAddress(address);
//            }
//            String station = fireStation.getId();
//            if(station != null){
//                currentFireStation.setId(id);
//            }
//            fireStationService.saveFireStation(currentFireStation);
//            return currentFireStation;
//        }
//        else{
//            return null;
//        }
//    }
//
//    @DeleteMapping("/firestation/{id}")
//    public void deleteFireStation(@PathVariable("id")final String id){
//        fireStationService.deleteFireStations(id);
//    }
//}
