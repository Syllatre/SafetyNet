package com.application.safetynet.repository;

import com.application.safetynet.model.FireStation;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import javax.annotation.PostConstruct;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


@Repository
public class InMemoryFireStationRepository implements FireStationRepository{



    private Map<String, FireStation>stringFireStationMap = new HashMap<>();

    public void init() throws IOException {
        String content = null;
        try {
            File file = ResourceUtils.getFile("classpath:data.json");
            content = Files.readString(file.toPath());
        }catch (IOException e){
        }
        Any fireStationsAny = JsonIterator.deserialize(content).get("firestations",'*');
        fireStationsAny.forEach(element->{
            String station = element.get("station").toString();
            String address = element.get("address").toString();
            if(!stringFireStationMap.containsKey(station)){
                ArrayList<String> addresses = new ArrayList<>();
                addresses.add(address);
                stringFireStationMap.put(station,new FireStation(station,addresses));
            }else{
                stringFireStationMap.get(station).addAddress(address);
            }
        });
     }


    @Override
    public List<FireStation> findAll() {
        return new ArrayList<>(stringFireStationMap.values());
    }

//    @Override
//    public Optional<FireStation> findById(String station) {
//        return Optional.ofNullable(station).map(stringFireStationMap::get);
//    }
//
//    @Override
//    public void deleteById(String station) {
//        stringFireStationMap.remove(station);
//    }
//
//    @Override
//    public FireStation save(FireStation fireStations) {
//        return null;
//    }
}
