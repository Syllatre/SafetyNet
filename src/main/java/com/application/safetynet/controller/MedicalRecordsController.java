package com.application.safetynet.controller;

import com.application.safetynet.exceptions.BadRequestException;
import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.service.MedicalRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordsController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordsController.class);

    MedicalRecordsService medicalRecordsService;

    public MedicalRecordsController(MedicalRecordsService medicalRecordsService) {
        this.medicalRecordsService = medicalRecordsService;
    }

    @GetMapping("/medicalrecords/findall")
    public List<MedicalRecord> findAll() {
        return medicalRecordsService.findAll();
    }

    @DeleteMapping("/medicalrecords")
    public void deletePerson(@RequestBody MedicalRecord medicalRecordsDelete) {
        if(medicalRecordsDelete.getFirstName()== null || medicalRecordsDelete.getLastName()== null) throw new BadRequestException("Merci d'indiquer le nom et prenom afin de pouvoir supprimer une personne");
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
