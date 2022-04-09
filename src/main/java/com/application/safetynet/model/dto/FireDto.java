package com.application.safetynet.model.dto;

import java.util.List;

public class FireDto {
    private List<FamilyByStationDto> deservedPeople;
    private List<String> stationNumber;

    public FireDto(List<FamilyByStationDto> deservedPeople, List<String> stationNumber) {
        this.deservedPeople = deservedPeople;
        this.stationNumber = stationNumber;
    }

    public FireDto(){}

    public List<FamilyByStationDto> getDeservedPeople() {
        return deservedPeople;
    }

    public void setDeservedPeople(List<FamilyByStationDto> deservedPeople) {
        this.deservedPeople = deservedPeople;
    }

    public List<String> getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(List<String> stationNumber) {
        this.stationNumber = stationNumber;
    }

    @Override
    public String toString() {
        return "FireDto{" +
                "deservedPeople=" + deservedPeople +
                ", stationNumber=" + stationNumber +
                '}';
    }
}
