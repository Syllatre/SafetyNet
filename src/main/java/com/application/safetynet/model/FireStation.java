package com.application.safetynet.model;


import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class FireStation {
    private String station;
    private List<String> addresses;

    public FireStation(String station, List<String> addresses) {
        this.station = station;
        this.addresses = addresses;
    }

    public FireStation() {
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

    public void addAddress(String address) {
        if (address == null) {
            addresses = new ArrayList<>();
        } else {
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
