package com.application.safetynet.model.dto;

import java.util.List;

public class CountChildAndAdultDto {
    private List<PersonDto> personList;
    private int personUnderEighteen;
    private int personOverEighteen;

    public CountChildAndAdultDto(List<PersonDto> personList, int personUnderEighteen, int personOverEighteen) {
        this.personList = personList;
        this.personUnderEighteen = personUnderEighteen;
        this.personOverEighteen = personOverEighteen;
    }

    public List<PersonDto> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonDto> personList) {
        this.personList = personList;
    }

    public int getPersonUnderEighteen() {
        return personUnderEighteen;
    }

    public void setPersonUnderEighteen(int personUnderEighteen) {
        this.personUnderEighteen = personUnderEighteen;
    }

    public int getPersonOverEighteen() {
        return personOverEighteen;
    }

    public void setPersonOverEighteen(int personOverEighteen) {
        this.personOverEighteen = personOverEighteen;
    }

    @Override
    public String toString() {
        return "CountChildAndAdult{" +
                "personList=" + personList +
                ", personUnderEighteen=" + personUnderEighteen +
                ", personOverEighteen=" + personOverEighteen +
                '}';
    }
}
