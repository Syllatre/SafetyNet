package com.application.safetynet.repository;

import com.application.safetynet.model.MedicalRecords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface MedicalRecordsRepository {
    void init() throws IOException;

    public List<MedicalRecords> findAll();

    public ArrayList<MedicalRecords> delete(MedicalRecords medicalRecordsDelete);

    public List<MedicalRecords> update(MedicalRecords medicalRecordsUpdate);

    public List<MedicalRecords> create(MedicalRecords medicalRecords);
}
