package com.application.safetynet.model.dto;


import lombok.Builder;

import java.util.List;

@Builder
public class FloodDto {
    private String address;
    private List<FamilyByStationDto> personOfFamily;

    public FloodDto(String address, List<FamilyByStationDto> personOfFamily) {
        this.address = address;
        this.personOfFamily = personOfFamily;
    }

    public FloodDto() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FamilyByStationDto> getPersonOfFamily() {
        return personOfFamily;
    }

    public void setPersonOfFamily(List<FamilyByStationDto> personOfFamily) {
        this.personOfFamily = personOfFamily;
    }

    @Override
    public String toString() {
        return "FloodDto{" +
                "address='" + address + '\'' +
                ", personOfFamily=" + personOfFamily +
                '}';
    }
}
