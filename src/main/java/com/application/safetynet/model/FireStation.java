package com.application.safetynet.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class FireStation {


    private String station;


    private List<String> addresses;



    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(String address)  {
        if (address == null){
            new ArrayList<>(this.addresses);
        }
        else{
            addresses.add(address);
        }
    }

    @Override
    public String toString() {
        return "FireStation{" +
                "station='" + station + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
