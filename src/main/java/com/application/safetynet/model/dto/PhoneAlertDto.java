package com.application.safetynet.model.dto;

import lombok.Builder;

@Builder
public class PhoneAlertDto {
    private String phone;

    public PhoneAlertDto(String phone) {
        this.phone = phone;
    }

    public PhoneAlertDto() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
