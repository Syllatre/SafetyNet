package com.application.safetynet.model;

public class FireStationDao {
    String station;
    String address;

    public FireStationDao(String station, String address) {
        this.station = station;
        this.address = address;
    }
    public FireStationDao(){}

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
        return "FireStationDao{" +
                "station='" + station + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
