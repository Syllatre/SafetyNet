package com.application.safetynet.controller;

import com.application.safetynet.model.MedicalRecords;
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


    @GetMapping("/medicalrecords")
    List<MedicalRecords> getMedicalRecords() {
        logger.info("All MedicalRecords http://localhost:8080/medicalrecords");
        return medicalRecordsService.getMedicalRecords();
    }

    @DeleteMapping("/medicalrecords")
    public void deletePerson(@RequestBody MedicalRecords medicalRecordsDelete) {
        logger.info("DELETE http://localhost:8080/medicalrecords");
        logger.info("body: " + medicalRecordsDelete);
        medicalRecordsService.delete(medicalRecordsDelete);
    }

    @PostMapping("/medicalrecords")
    public List<MedicalRecords> addMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        logger.info("POST http://localhost:8080/medicalrecords");
        logger.info("body: " + medicalRecords);
        return medicalRecordsService.createMedicalRecords(medicalRecords);
    }

    @PutMapping("/medicalrecords")
    List<MedicalRecords> updateMedicalRecords(@RequestBody MedicalRecords medicalRecordsUpdate) {
        logger.info("PUT http://localhost:8080/medicalrecords");
        logger.info("body: " + medicalRecordsUpdate);
        return medicalRecordsService.update(medicalRecordsUpdate);
    }
}
