package com.application.safetynet.repository;

import com.application.safetynet.model.MedicalRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryMedicalRecordsTest {
    MedicalRecordsRepository inMemoryMedicalRecordsRepository = new InMemoryMedicalRecordsRepository();

    @Test
    public void initTest() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        List<MedicalRecord> medicalRecordsList = inMemoryMedicalRecordsRepository.findAll();
        System.out.println(medicalRecordsList);
        Assertions.assertEquals(medicalRecordsList.size(), 23);
        Assertions.assertNotNull(medicalRecordsList);
    }

    @Test
    public void update() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        List<String>medication = new ArrayList<>();
        medication.add("doliprane");
        List<String> allergies = new ArrayList<>();
        medication.add(" ");
        MedicalRecord updateMedicalRecords = new MedicalRecord("Zach","Zemicks","03/06/2017",medication,allergies);

        List<MedicalRecord> medicalRecordsList = inMemoryMedicalRecordsRepository.update(updateMedicalRecords);
        Assertions.assertEquals(medicalRecordsList.size(), 23);
        Assertions.assertTrue(medicalRecordsList.stream().anyMatch(element -> element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks") && element.getMedications().contains("doliprane")));
    }

    @Test
    public void findAllTest() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        List<MedicalRecord> medicalRecordsList = inMemoryMedicalRecordsRepository.findAll();
        Assertions.assertNotNull(medicalRecordsList);
        Assertions.assertEquals(medicalRecordsList.size(), 23);
    }

    @Test
    public void create() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        List<String>medications = new ArrayList<>();
        medications.add("Ventoline");
        List<String> allergies = new ArrayList<>();
        allergies.contains("cat");
        MedicalRecord createMedicalRecords = new MedicalRecord("Aimen","Jerbi","30/08/1981",medications,allergies);
        List<MedicalRecord> medicalRecordsList = inMemoryMedicalRecordsRepository.create(createMedicalRecords);
        inMemoryMedicalRecordsRepository.findAll();
        Assertions.assertTrue(medicalRecordsList.stream().anyMatch(element -> element.getFirstName().equals("Aimen") && element.getLastName().equals("Jerbi") && element.getMedications().contains("Ventoline")));
    }

    @Test
    public void deleteIfTrue() throws Exception {
        inMemoryMedicalRecordsRepository.init();
        MedicalRecord deleteMedicalRecords = new MedicalRecord();
        deleteMedicalRecords.setFirstName("Zach");
        deleteMedicalRecords.setLastName("Zemicks");
        inMemoryMedicalRecordsRepository.delete(deleteMedicalRecords);
        List<MedicalRecord> refreshList = new ArrayList<>(inMemoryMedicalRecordsRepository.findAll());
        Assertions.assertEquals(refreshList.size(), 22);
        Assertions.assertFalse(refreshList.stream().anyMatch(element -> element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks")));
    }

    @Test
    public void deleteIfFalse() throws Exception {
        inMemoryMedicalRecordsRepository.init();
        MedicalRecord medicalRecordsDelete = new MedicalRecord();
        medicalRecordsDelete.setFirstName("");
        medicalRecordsDelete.setLastName("Zemicks");
        inMemoryMedicalRecordsRepository.delete(medicalRecordsDelete);
        List<MedicalRecord> refreshList = new ArrayList<>(inMemoryMedicalRecordsRepository.findAll());
        Assertions.assertEquals(refreshList.size(), 23);
        Assertions.assertTrue(refreshList.stream().anyMatch(element -> element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks")));
    }
}
