package com.application.safetynet.repository;

import com.application.safetynet.model.MedicalRecords;
import com.application.safetynet.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class InMemoryMedicalRecordsTest {
    MedicalRecordsRepository inMemoryMedicalRecordsRepository = new InMemoryMedicalRecordsRepository();

    @Test
    public void initTest() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        List<MedicalRecords> medicalRecordsList = inMemoryMedicalRecordsRepository.findAll();
        System.out.println(medicalRecordsList);
        Assertions.assertEquals(medicalRecordsList.size(), 23);
        Assertions.assertNotNull(medicalRecordsList);
    }

    @Test
    public void update() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        MedicalRecords updateMedicalRecords = new MedicalRecords();
        updateMedicalRecords.setFirstName("Zach");
        updateMedicalRecords.setLastName("Zemicks");
        updateMedicalRecords.setBirthdate("03/06/2017");
        updateMedicalRecords.setMedications("[doliprane]");

        List<MedicalRecords> medicalRecordsList = inMemoryMedicalRecordsRepository.update(updateMedicalRecords);
        Assertions.assertEquals(medicalRecordsList.size(), 23);
        Assertions.assertTrue(medicalRecordsList.stream().anyMatch(element -> element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks") && element.getMedications().equals("[doliprane]")));
    }

    @Test
    public void findAllTest() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        List<MedicalRecords> medicalRecordsList = inMemoryMedicalRecordsRepository.findAll();
        Assertions.assertNotNull(medicalRecordsList);
        Assertions.assertEquals(medicalRecordsList.size(), 23);
    }

    @Test
    public void create() throws IOException {
        inMemoryMedicalRecordsRepository.init();
        MedicalRecords createMedicalRecords = new MedicalRecords();
        createMedicalRecords.setFirstName("Aimen");
        createMedicalRecords.setLastName("Jerbi");
        createMedicalRecords.setBirthdate("30/08/1981");
        createMedicalRecords.setMedications("[Ventoline]");
        createMedicalRecords.setAllergies("[]");

        List<MedicalRecords> medicalRecordsList = inMemoryMedicalRecordsRepository.create(createMedicalRecords);
        Assertions.assertTrue(medicalRecordsList.stream().anyMatch(element -> element.getFirstName().equals("Aimen") && element.getLastName().equals("Jerbi") && element.getMedications().equals("[Ventoline]")));
    }

    @Test
    public void deleteIfTrue() throws Exception {
        inMemoryMedicalRecordsRepository.init();
        MedicalRecords deleteMedicalRecords = new MedicalRecords();
        deleteMedicalRecords.setFirstName("Zach");
        deleteMedicalRecords.setLastName("Zemicks");
        List<MedicalRecords> delete = inMemoryMedicalRecordsRepository.delete(deleteMedicalRecords);
        Assertions.assertEquals(delete.size(), 22);
        Assertions.assertFalse(delete.stream().anyMatch(element -> element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks")));
    }

    @Test
    public void deleteIfFalse() throws Exception {
        inMemoryMedicalRecordsRepository.init();
        MedicalRecords medicalRecordsDelete = new MedicalRecords();
        medicalRecordsDelete.setFirstName("");
        medicalRecordsDelete.setLastName("Zemicks");
        List<MedicalRecords> delete = inMemoryMedicalRecordsRepository.delete(medicalRecordsDelete);
        Assertions.assertEquals(delete.size(), 23);
        Assertions.assertTrue(delete.stream().anyMatch(element -> element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks")));
    }
}
