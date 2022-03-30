package com.application.safetynet.model.dto;

import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Builder
public class PersonWithMedicalRecordAndAgeDto {

    @NotNull
    private String lastName;
    @NotNull
    private String phone;
    private int age;
    private List<String> station;
    private List<String> medications;
    private List<String> allergies;

    public PersonWithMedicalRecordAndAgeDto(String lastName, String phone, int age, List<String> station, List<String> medications, List<String> allergies) {
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.station = station;
        this.medications = medications;
        this.allergies = allergies;
    }

    public PersonWithMedicalRecordAndAgeDto(){}

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getStation() {
        return station;
    }

    public void setStation(List<String> station) {
        this.station = station;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    @Override
    public String toString() {
        return "PersonWithMedicalRecordAndAgeDto{" +
                "lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", station=" + station +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}
