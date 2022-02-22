package com.application.safetynet.repository;

import com.application.safetynet.model.FireStation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.logging.log4j.core.jackson.ListOfMapEntryDeserializer;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;


public class InMemoryFireStationRepository implements FireStationRepository{

    @Autowired
    FireStation fireStation;

    @Autowired


    private Map<String, List<FireStation>>stringFireStationMap = new HashMap<>();
    @Override
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode fullList = null;
        try {
            File file = ResourceUtils.getFile("classpath:data.json");
            String content = Files.readString(file.toPath());
            fullList = mapper.readTree(content);
        }catch (IOException e){
        }

        JsonNode fireStationArray = fullList.get("firestations");
        Iterator<JsonNode> fireStationNode = fireStationArray.elements();
        List<String>numberStations = new ArrayList<>();
        List<FireStation> fireStationList = new ArrayList<>();
        while (fireStationNode.hasNext()){
            FireStation fireStation ;
            JsonNode elementFireStation = fireStationNode.next();
            String numberStation = elementFireStation.get("station").toString();
            int id  = Integer.parseInt(numberStation);
            String addressFireStation = elementFireStation.get("address").toString();
            if(!numberStations.contains(numberStation)){
                fireStation =new FireStation();
                fireStation.setStation(numberStation);
                numberStations.add(numberStation);
                fireStationList.add(id,fireStation);
                numberStations.add(numberStation);
                stringFireStationMap.put(numberStation, fireStationList);
            }else{
                fireStation = fireStationList.get(id);
            }
            fireStation.addAddress(addressFireStation);
        }
    }


    @Override
    public Iterable<FireStation> findAll() {
        return null;
    }

    @Override
    public Optional<FireStation> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public FireStation save(FireStation fireStations) {
        return null;
    }
}
