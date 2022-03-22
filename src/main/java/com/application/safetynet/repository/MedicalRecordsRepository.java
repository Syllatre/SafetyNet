package com.application.safetynet.repository;

import com.application.safetynet.model.MedicalRecord;

import java.io.IOException;
import java.util.List;

public interface MedicalRecordsRepository {
    void init() throws IOException;

    public List<MedicalRecord> findAll();

    public void delete(MedicalRecord medicalRecordsDelete);

    public List<MedicalRecord> update(MedicalRecord medicalRecordsUpdate);

    public List<MedicalRecord> create(MedicalRecord medicalRecords);
}
