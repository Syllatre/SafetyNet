package com.application.safetynet.model.dto;

import lombok.Builder;

@Builder
public class PersonEmail {
    private String email;

    public PersonEmail(String email) {
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
