package com.application.safetynet.repository;

import com.application.safetynet.model.MedicalRecord;

import java.io.IOException;
import java.util.List;

public interface MedicalRecordsRepository {
    void init() throws IOException;

    List<MedicalRecord> findAll();

    void delete(MedicalRecord medicalRecordsDelete);

    MedicalRecord update(MedicalRecord medicalRecordsUpdate);

    MedicalRecord create(MedicalRecord medicalRecords);
}
