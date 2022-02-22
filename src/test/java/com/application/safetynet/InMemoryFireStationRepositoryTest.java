package com.application.safetynet;

import com.application.safetynet.datajson.SplitDataJson;
import com.application.safetynet.model.FireStation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;


import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static com.jsoniter.JsonIterator.deserialize;

public class InMemoryFireStationRepositoryTest {

    private Map<String, List<FireStation>>stringFireStationMap = new HashMap<>();
    @Test
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
            numberStation = numberStation.substring(1,1);
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

//        try {
//            List<FireStation> fireStationList = new ArrayList<>();
//            ObjectMapper mapper = new ObjectMapper();
//            File file = ResourceUtils.getFile("classpath:data.json");
//            String content = Files.readString(file.toPath());
//            JsonNode jsonNode = mapper.readTree(content);
//            String fire = jsonNode.get("firestations").toString();
//            fireStationList = mapper.readValue(fire, List.class);
//            Map<String,List<FireStation>> finalMap = fireStationList.stream().collect(Collectors.groupingBy(fireStation->fireStation.getStation()));
//            System.out.println(finalMap);
//        } catch (IOException e) {}
    }
}