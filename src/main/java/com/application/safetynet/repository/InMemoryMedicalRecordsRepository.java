package com.application.safetynet.repository;

import com.application.safetynet.model.MedicalRecord;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryMedicalRecordsRepository implements MedicalRecordsRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryMedicalRecordsRepository.class);

    private  final Map<String, MedicalRecord> stringMedicalRecordMap = new HashMap<>();



    @PostConstruct
    public void init() throws IOException {
        String content = null;
        try {
            File file = ResourceUtils.getFile("classpath:data.json");
            content = Files.readString(file.toPath());
        } catch (IOException ignored) {
        }
        assert content != null;
        Any medicalRecordsAny = JsonIterator.deserialize(content).get("medicalrecords", '*');
        medicalRecordsAny.forEach(element -> {
            String firstName = element.get("firstName").toString();
            String lastName = element.get("lastName").toString();
            String birthdate = element.get("birthdate").toString();
            List<String> medications = new ArrayList<>();
            List<String> allergies = new ArrayList<>();
            String id = firstName+" "+lastName;
            for(int nb = 0;nb<element.get("medications").size();nb++){
                String medication = element.get("medications",nb).toString();
                medications.add(medication);
            }
            for(int nb = 0;nb<element.get("allergies").size();nb++){
                String allergie = element.get("allergies",nb).toString();
                allergies.add(allergie);
            }
            stringMedicalRecordMap.put(id, new MedicalRecord(firstName, lastName, birthdate, medications,allergies));
        });
    }

    @Override
    public List<MedicalRecord> findAll() {
        return new ArrayList<>(stringMedicalRecordMap.values());
    }

    @Override
    public void delete(MedicalRecord medicalRecordsDelete) {
        boolean deleted = stringMedicalRecordMap.values().removeIf(medicalRecords -> medicalRecordsDelete.getFirstName().equalsIgnoreCase(medicalRecords.getFirstName())
                && medicalRecordsDelete.getLastName().equalsIgnoreCase(medicalRecords.getLastName()));
        if (deleted) {
            logger.info(medicalRecordsDelete.getFirstName() + " " + medicalRecordsDelete.getLastName() + " is delete");
            logger.info("now there is " + stringMedicalRecordMap.size() + " persons");
        } else {
            logger.error("nobody knows as" + medicalRecordsDelete.getFirstName() + " " + medicalRecordsDelete.getLastName());
        }
    }

    @Override
    public  MedicalRecord update(MedicalRecord medicalRecordsUpdate) {
        for (MedicalRecord medicalRecords : stringMedicalRecordMap.values()) {
            if (medicalRecords.getFirstName().equalsIgnoreCase(medicalRecordsUpdate.getFirstName())
                    && medicalRecords.getLastName().equalsIgnoreCase(medicalRecordsUpdate.getLastName())) {
            medicalRecords.setBirthdate(medicalRecordsUpdate.getBirthdate());
            medicalRecords.setMedications(medicalRecordsUpdate.getMedications());
            medicalRecords.setAllergies(medicalRecordsUpdate.getAllergies());
            }
        }
        return medicalRecordsUpdate;
    }

    @Override
    public MedicalRecord create(MedicalRecord medicalRecords) {
        logger.info(medicalRecords.getFirstName() + " " + medicalRecords.getLastName());
        try {
            stringMedicalRecordMap.put(medicalRecords.getFirstName() + " " + medicalRecords.getLastName(), medicalRecords);
            logger.info(medicalRecords.getFirstName() + " " + medicalRecords.getLastName() + " is added");
        } catch (Exception e) {
            logger.error("failed to add the MedicalRecords", e);
        }
        return medicalRecords;
    }
}
