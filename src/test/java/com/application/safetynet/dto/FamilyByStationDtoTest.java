package com.application.safetynet.dto;

import com.application.safetynet.model.dto.FamilyByStationDto;
import com.application.safetynet.model.dto.PhoneAlertDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FamilyByStationDtoTest {
    FamilyByStationDto familyByStationDto = new FamilyByStationDto("toto","tata","1234",25, List.of("doliprane"),List.of("humidite"));

    @Test
    final void setFirsNameTest() {
        FamilyByStationDto familyByStationDto1 = new FamilyByStationDto();
        familyByStationDto1.setFirstName("toto");
        Assert.assertEquals(familyByStationDto.getFirstName(), familyByStationDto1.getFirstName());
    }

    @Test
    final void setLastNameTest() {
        FamilyByStationDto familyByStationDto1 = new FamilyByStationDto();
        familyByStationDto1.setLastName("tata");
        Assert.assertEquals(familyByStationDto.getLastName(), familyByStationDto1.getLastName());
    }

    @Test
    final void setPhoneTest() {
        FamilyByStationDto familyByStationDto1 = new FamilyByStationDto();
        familyByStationDto1.setPhone("1234");
        Assert.assertEquals(familyByStationDto.getPhone(), familyByStationDto1.getPhone());
    }

    @Test
    final void setAgeTest() {
        FamilyByStationDto familyByStationDto1 = new FamilyByStationDto();
        familyByStationDto1.setAge(25);
        Assert.assertEquals(familyByStationDto.getAge(), familyByStationDto1.getAge());
    }

    @Test
    final void setMedicationTest() {
        FamilyByStationDto familyByStationDto1 = new FamilyByStationDto();
        familyByStationDto1.setMedications(List.of("doliprane"));
        Assert.assertEquals(familyByStationDto.getMedications(), familyByStationDto1.getMedications());
    }

    @Test
    final void setAllergieTest() {
        FamilyByStationDto familyByStationDto1 = new FamilyByStationDto();
        familyByStationDto1.setAllergies(List.of("humidite"));
        Assert.assertEquals(familyByStationDto.getAllergies(), familyByStationDto1.getAllergies());
    }
}
