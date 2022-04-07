package com.application.safetynet.repository;

import com.application.safetynet.model.MedicalRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryMedicalRecordsTest {
    MedicalRecordsRepository inMemoryMedicalRecordsRepository = new InMemoryMedicalRecordsRepository();

    @Test
    public void initTest() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        List<MedicalRecord> medicalRecordsList = inMemoryMedicalRecordsRepository.findAll();
        System.out.println(medicalRecordsList);
        assertEquals(medicalRecordsList.size(), 23);
        assertNotNull(medicalRecordsList);
    }

    @Test
    public void update() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        List<String> medication = new ArrayList<>();
        medication.add("doliprane");
        List<String> allergies = new ArrayList<>();
        medication.add(" ");
        MedicalRecord updateMedicalRecords = new MedicalRecord("Zach", "Zemicks", "03/06/2017", medication, allergies);

        MedicalRecord medicalRecords = inMemoryMedicalRecordsRepository.update(updateMedicalRecords);
        assertEquals(medicalRecords.getBirthdate(), "03/06/2017");
    }

    @Test
    public void findAllTest() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        List<MedicalRecord> medicalRecordsList = inMemoryMedicalRecordsRepository.findAll();
        assertNotNull(medicalRecordsList);
        assertEquals(medicalRecordsList.size(), 23);
    }

    @Test
    public void create() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        List<String> medications = new ArrayList<>();
        medications.add("Ventoline");
        List<String> allergies = new ArrayList<>();
        allergies.contains("cat");
        MedicalRecord createMedicalRecords = new MedicalRecord("Aimen", "Jerbi", "30/08/1981", medications, allergies);
        MedicalRecord medicalRecord = inMemoryMedicalRecordsRepository.create(createMedicalRecords);
        List<MedicalRecord> medicalRecordList = inMemoryMedicalRecordsRepository.findAll();
        assertTrue(medicalRecordList.stream().anyMatch(element -> element.getFirstName().equals("Aimen") && element.getLastName().equals("Jerbi") && element.getMedications().contains("Ventoline")));
    }

    @Test
    public void deleteIfTrue() throws Exception {
        inMemoryMedicalRecordsRepository.init();
        MedicalRecord deleteMedicalRecords = new MedicalRecord();
        deleteMedicalRecords.setFirstName("Zach");
        deleteMedicalRecords.setLastName("Zemicks");
        inMemoryMedicalRecordsRepository.delete(deleteMedicalRecords);
        List<MedicalRecord> refreshList = new ArrayList<>(inMemoryMedicalRecordsRepository.findAll());
        assertEquals(refreshList.size(), 22);
        assertFalse(refreshList.stream().anyMatch(element -> element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks")));
    }

    @Test
    public void deleteIfFalse() throws Exception {
        inMemoryMedicalRecordsRepository.init();
        MedicalRecord medicalRecordsDelete = new MedicalRecord();
        medicalRecordsDelete.setFirstName("");
        medicalRecordsDelete.setLastName("Zemicks");
        inMemoryMedicalRecordsRepository.delete(medicalRecordsDelete);
        List<MedicalRecord> refreshList = new ArrayList<>(inMemoryMedicalRecordsRepository.findAll());
        assertEquals(refreshList.size(), 23);
        assertTrue(refreshList.stream().anyMatch(element -> element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks")));
    }
}
