package com.application.safetynet.controller;

import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.service.MedicalRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordsController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordsController.class);

    @Autowired
    MedicalRecordsService medicalRecordsService;



    @DeleteMapping("/medicalrecords")
    public void deletePerson(@RequestBody MedicalRecord medicalRecordsDelete) {
        logger.info("DELETE http://localhost:8080/medicalrecords");
        logger.info("body: " + medicalRecordsDelete);
        medicalRecordsService.delete(medicalRecordsDelete);
    }

    @PostMapping("/medicalrecords")
    public List<MedicalRecord> addMedicalRecords(@RequestBody MedicalRecord medicalRecord) {
        logger.info("POST http://localhost:8080/medicalrecords");
        logger.info("body: " + medicalRecord);
        return medicalRecordsService.createMedicalRecords(medicalRecord);
    }

    @PutMapping("/medicalrecords")
    List<MedicalRecord> updateMedicalRecords(@RequestBody MedicalRecord medicalRecordUpdate) {
        logger.info("PUT http://localhost:8080/medicalrecords");
        logger.info("body: " + medicalRecordUpdate);
        return medicalRecordsService.update(medicalRecordUpdate);
    }
}
