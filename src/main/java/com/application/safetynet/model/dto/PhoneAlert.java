package com.application.safetynet.model.dto;

import lombok.Builder;

@Builder
public class PhoneAlert {
    private String phone;

    public PhoneAlert(String phone) {
        this.phone = phone;
    }

    public PhoneAlert() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
