package com.application.safetynet.repository;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.FireStationDto;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Repository
public class InMemoryFireStationRepository implements FireStationRepository{
    private static Logger logger = LoggerFactory.getLogger(InMemoryFireStationRepository.class);


    private Map<String, FireStation>stringFireStationMap = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {
        String content = null;
        try {
            logger.info(" FireStation data initialized");
            File file = ResourceUtils.getFile("classpath:data.json");
            content = Files.readString(file.toPath());
        }catch (IOException e){
            logger.error("failed to load firestation data", e);
        }
        Any fireStationsAny = JsonIterator.deserialize(content).get("firestations",'*');
        fireStationsAny.forEach(element->{
            String station = element.get("station").toString();
            String address = element.get("address").toString();
            if (!stringFireStationMap.containsKey(station)) {
                ArrayList<String> addresses = new ArrayList<>();
                addresses.add(address);
                stringFireStationMap.put(station, new FireStation(station, addresses));
            }else{
                stringFireStationMap.get(station).addAddress(address);
            }
        });
     }


    @Override
    public List<FireStation> findAll() {
        logger.info("show all fire station");
        return new ArrayList<>(stringFireStationMap.values());
    }

    @Override
    public Optional<FireStation> findByStation(String station) {
        return Optional.ofNullable(station).map(stringFireStationMap::get);
    }

    @Override
    public ArrayList<FireStation> deleteFireStation(FireStationDto id) {
        if(id.getAddress() != null) {
            try{
            stringFireStationMap.entrySet().removeIf(e -> e.getValue().getAddresses().contains(id.getAddress()));
            logger.info(id.getAddress() +  "is deleted");
            logger.info("now there is " + stringFireStationMap.size() + " Firestations");
            return new ArrayList<>(stringFireStationMap.values());}
            catch (Exception e){
                logger.error("failed to add the Firestation", e);
            }
        }
        if(id.getStation() != null) {
            try {
                stringFireStationMap.entrySet().removeIf(e -> e.getValue().getStation().equals(id.getStation()));
                logger.info(id.getStation() +  "is deleted");
                logger.info("now there is " + stringFireStationMap.size() + " Firestations");
            }catch(Exception e){
                logger.error("failed to add the Firestation", e);
            }
        }
        return new ArrayList<>(stringFireStationMap.values());
    }


    @Override
    public FireStation save(FireStation fireStations) {
            return stringFireStationMap.put(fireStations.getStation(),fireStations);
    }


}
