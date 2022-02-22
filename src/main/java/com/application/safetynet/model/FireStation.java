package com.application.safetynet.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FireStation {



    private String station;
    private String address;
    private List<String> addresses;

    public void addAddress(String address) {

        this.addresses.add(address);

    }


    public String getStation() {
        return station;
    }


    public void setStation(String station) {
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "FireStation{" +
                "station='" + station + '\'' +
                ", address='" + address + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}

