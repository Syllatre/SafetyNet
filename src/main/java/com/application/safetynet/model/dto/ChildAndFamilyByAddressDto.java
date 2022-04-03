package com.application.safetynet.model.dto;

import com.application.safetynet.model.Person;

import java.util.List;

public class ChildAndFamilyByAddressDto {
    List<Person> other;
    List<ChildDto> child;

    public ChildAndFamilyByAddressDto(List<Person> other, List<ChildDto> child) {
        this.other = other;
        this.child = child;
    }

    public ChildAndFamilyByAddressDto(){}

    @Override
    public String toString() {
        return "ChilAndFamilyByAddressDto{" +
                "other=" + other +
                ", child=" + child +
                '}';
    }
}
