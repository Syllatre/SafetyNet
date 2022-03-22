package com.application.safetynet.service;

import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.repository.MedicalRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordsService {
    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;


    public List<MedicalRecord> createMedicalRecords(MedicalRecord medicalRecord) {
        return medicalRecordsRepository.create(medicalRecord);
    }

    public void delete(MedicalRecord medicalRecordsDelete) {
         medicalRecordsRepository.delete(medicalRecordsDelete);
    }

    public List<MedicalRecord> update(MedicalRecord medicalRecordsUpdate) {
        return medicalRecordsRepository.update(medicalRecordsUpdate);
    }
}
