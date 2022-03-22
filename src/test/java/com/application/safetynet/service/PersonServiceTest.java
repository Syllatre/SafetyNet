package com.application.safetynet.service;

import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.repository.MedicalRecordsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;


public class PersonServiceTest {


    @InjectMocks
    PersonService personService = new PersonService();

    @Mock
    MedicalRecordsRepository medicalRecordsRepository;





    @Test
    void stringMedicalRecordMapTest() {
        Map<String, MedicalRecord> stringMedicalRecordMap = personService.stringMedicalRecordMap();
        Assertions.assertEquals(stringMedicalRecordMap.size(), 20);

    }

    @Test
    void getPersonByStationAddress() {
    }

    @Test
    void ageCalculation() {
    }

    @Test
    void countAdultAndChild() {
    }

    @Test
    void getChildAndFamilyByAddress() {
    }
}
