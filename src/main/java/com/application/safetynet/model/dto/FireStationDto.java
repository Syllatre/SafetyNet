package com.application.safetynet.model.dto;

public class FireStationDto {
    private String station;
    private String address;

    public FireStationDto(String station, String address) {
        this.station = station;
        this.address = address;
    }

    public FireStationDto() {
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
        return "FireStationDto{" +
                "station='" + station + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

}
