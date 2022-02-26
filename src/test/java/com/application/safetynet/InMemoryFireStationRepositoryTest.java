package com.application.safetynet;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.repository.InMemoryFireStationRepository;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InMemoryFireStationRepositoryTest {


    Map<String, FireStation> stringFireStationMap = new HashMap<>();
    @Test
    public void initTest() throws IOException {

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
        System.out.println(stringFireStationMap);
    }
}





