package com.application.safetynet.controller;

import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.service.MedicalRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordsController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordsController.class);

    @Autowired
    MedicalRecordsService medicalRecordsService;


    @DeleteMapping("/medicalrecords")
    public void deletePerson(@RequestBody MedicalRecord medicalRecordsDelete) {
        logger.debug("Deleting medical record {}", medicalRecordsDelete);
        medicalRecordsService.delete(medicalRecordsDelete);
    }

    @PostMapping("/medicalrecords")
    public MedicalRecord addMedicalRecords(@RequestBody MedicalRecord medicalRecord) {
        logger.debug("Creating medical record {}", medicalRecord);
        return medicalRecordsService.createMedicalRecords(medicalRecord);
    }

    @PutMapping("/medicalrecords")
    MedicalRecord updateMedicalRecords(@RequestBody MedicalRecord medicalRecordUpdate) {
        logger.debug("Updating medical record {}", medicalRecordUpdate);
        return medicalRecordsService.update(medicalRecordUpdate);
    }
}
