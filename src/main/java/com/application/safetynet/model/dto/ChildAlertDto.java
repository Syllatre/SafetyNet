package com.application.safetynet.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class ChildAlertDto {
    private String firstName;
    private String lastName;
    private int age;
    private List<FamilyMemberDto> familyMembers;

    public ChildAlertDto(String firstName, String lastName, int age, List<FamilyMemberDto> familyMembers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.familyMembers = familyMembers;
    }

    public ChildAlertDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<FamilyMemberDto> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMemberDto> familyMembers) {
        this.familyMembers = familyMembers;
    }


    @Override
    public String toString() {
        return "ChildAlertDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", familyMembers=" + familyMembers +
                '}';
    }
}
