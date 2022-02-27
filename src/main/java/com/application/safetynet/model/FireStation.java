package com.application.safetynet.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class FireStation {


    private String station;
    private List<String> addresses;

    public FireStation(String station, ArrayList<String> addresses) {
    }

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
            address = "empty";
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
