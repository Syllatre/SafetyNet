package com.application.safetynet.service;

import com.application.safetynet.model.MedicalRecords;
import com.application.safetynet.repository.MedicalRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordsService {
    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    public List<MedicalRecords> getMedicalRecords() {
        return medicalRecordsRepository.findAll();
    }

    public List<MedicalRecords> createMedicalRecords(MedicalRecords medicalRecords) {
        return medicalRecordsRepository.create(medicalRecords);
    }

    public List<MedicalRecords> delete(MedicalRecords medicalRecordsDelete) {
        return medicalRecordsRepository.delete(medicalRecordsDelete);
    }

    public List<MedicalRecords> update(MedicalRecords medicalRecordsUpdate) {
        return medicalRecordsRepository.update(medicalRecordsUpdate);
    }
}
