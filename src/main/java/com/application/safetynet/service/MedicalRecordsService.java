package com.application.safetynet.service;

import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.repository.MedicalRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordsService {
    MedicalRecordsRepository medicalRecordsRepository;

    public MedicalRecordsService(MedicalRecordsRepository medicalRecordsRepository) {
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    public MedicalRecord createMedicalRecords(MedicalRecord medicalRecord) {
        return medicalRecordsRepository.create(medicalRecord);
    }

    public void delete(MedicalRecord medicalRecordsDelete) {
         medicalRecordsRepository.delete(medicalRecordsDelete);
    }

    public MedicalRecord update(MedicalRecord medicalRecordsUpdate) {
        return medicalRecordsRepository.update(medicalRecordsUpdate);
    }

    public List<MedicalRecord> findAll(){
        return medicalRecordsRepository.findAll();
    }
}
