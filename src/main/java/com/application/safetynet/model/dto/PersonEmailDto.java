package com.application.safetynet.model.dto;

import lombok.Builder;

@Builder
public class PersonEmailDto {
    private String email;

    public PersonEmailDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PersonEmail{" +
                "email='" + email + '\'' +
                '}';
    }
}
