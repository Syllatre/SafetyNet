package com.application.safetynet.dto;

import com.application.safetynet.model.dto.PhoneAlertDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class PhoneAlertDtoTest {

    PhoneAlertDto phoneAlertDto = new com.application.safetynet.model.dto.PhoneAlertDto("1234");

    @Test
    final void phoneAlertDtoTest() {
        com.application.safetynet.model.dto.PhoneAlertDto phoneAlertDto1 = new com.application.safetynet.model.dto.PhoneAlertDto();
        phoneAlertDto1.setPhone("1234");
        Assert.assertEquals(phoneAlertDto.getPhone(), phoneAlertDto1.getPhone());
    }
}
