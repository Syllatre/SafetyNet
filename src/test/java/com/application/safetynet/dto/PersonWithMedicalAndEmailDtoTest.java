package com.application.safetynet.dto;

import com.application.safetynet.model.dto.PersonWithMedicalAndEmailDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;


public class PersonWithMedicalAndEmailDtoTest {

    PersonWithMedicalAndEmailDto personWithMedicalAndEmailDto = new PersonWithMedicalAndEmailDto("toto", "soso", "rue des babas", "akira@gmail.com", 28, List.of("doliprane"), List.of("humidite"));

    @Test
    final void setFirsNameTest() {
        PersonWithMedicalAndEmailDto personWithMedicalAndEmailDto1 = new PersonWithMedicalAndEmailDto();
        personWithMedicalAndEmailDto1.setFirstName("toto");
        Assert.assertEquals(personWithMedicalAndEmailDto.getFirstName(), personWithMedicalAndEmailDto1.getFirstName());
    }

    @Test
    final void setLastNameTest() {
        PersonWithMedicalAndEmailDto personWithMedicalAndEmailDto1 = new PersonWithMedicalAndEmailDto();
        personWithMedicalAndEmailDto1.setLastName("soso");
        Assert.assertEquals(personWithMedicalAndEmailDto.getLastName(), personWithMedicalAndEmailDto1.getLastName());
    }

    @Test
    final void setAddressTest() {
        PersonWithMedicalAndEmailDto personWithMedicalAndEmailDto1 = new PersonWithMedicalAndEmailDto();
        personWithMedicalAndEmailDto1.setAddress("rue des babas");
        Assert.assertEquals(personWithMedicalAndEmailDto.getAddress(), personWithMedicalAndEmailDto1.getAddress());
    }

    @Test
    final void setEmailTest() {
        PersonWithMedicalAndEmailDto personWithMedicalAndEmailDto1 = new PersonWithMedicalAndEmailDto();
        personWithMedicalAndEmailDto1.setEmail("akira@gmail.com");
        Assert.assertEquals(personWithMedicalAndEmailDto.getEmail(), personWithMedicalAndEmailDto1.getEmail());
    }

}
