package com.application.safetynet.repository;

import com.application.safetynet.model.MedicalRecords;
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

    private final Map<String, MedicalRecords> stringMedicalRecordsMap = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {
        String content = null;
        try {
            logger.info(" MedicalRecords data initialized");
            File file = ResourceUtils.getFile("classpath:data.json");
            content = Files.readString(file.toPath());
        } catch (IOException e) {
            logger.error("failed to load MedicalRecords data", e);
        }
        Any medicalRecordsAny = JsonIterator.deserialize(content).get("medicalrecords", '*');
        medicalRecordsAny.forEach(element -> {
            String firstName = element.get("firstName").toString();
            String lastName = element.get("lastName").toString();
            String birthdate = element.get("birthdate").toString();
            String medications = element.get("medications").toString();
            String allergies = element.get("allergies").toString();
            String id = firstName + " " + lastName;
            stringMedicalRecordsMap.put(id, new MedicalRecords(firstName, lastName, birthdate, medications, allergies));
        });
    }

    @Override
    public List<MedicalRecords> findAll() {
        return new ArrayList<>(stringMedicalRecordsMap.values());
    }

    @Override
    public ArrayList<MedicalRecords> delete(MedicalRecords medicalRecordsDelete) {
        boolean deleted = stringMedicalRecordsMap.values().removeIf(medicalRecords -> medicalRecordsDelete.getFirstName().equalsIgnoreCase(medicalRecords.getFirstName())
                && medicalRecordsDelete.getLastName().equalsIgnoreCase(medicalRecords.getLastName()));
        if (deleted) {
            logger.info(medicalRecordsDelete.getFirstName() + " " + medicalRecordsDelete.getLastName() + " is delete");
            logger.info("now there is " + stringMedicalRecordsMap.size() + " persons");
        } else {
            logger.error("nobody knows as" + medicalRecordsDelete.getFirstName() + " " + medicalRecordsDelete.getLastName());
        }
        return new ArrayList<>(stringMedicalRecordsMap.values());
    }

    @Override
    public List<MedicalRecords> update(MedicalRecords medicalRecordsUpdate) {
        for (MedicalRecords medicalRecords : stringMedicalRecordsMap.values()) {
            if (medicalRecords.getFirstName().equalsIgnoreCase(medicalRecordsUpdate.getFirstName())
                    && medicalRecords.getLastName().equalsIgnoreCase(medicalRecordsUpdate.getLastName())) ;
            medicalRecords.setBirthdate(medicalRecordsUpdate.getBirthdate());
            medicalRecords.setMedications(medicalRecordsUpdate.getMedications());
            medicalRecords.setAllergies(medicalRecordsUpdate.getAllergies());
        }
        return new ArrayList<>(stringMedicalRecordsMap.values());
    }

    @Override
    public List<MedicalRecords> create(MedicalRecords medicalRecords) {
        logger.info(medicalRecords.getFirstName() + " " + medicalRecords.getLastName());
        try {
            stringMedicalRecordsMap.put(medicalRecords.getFirstName() + " " + medicalRecords.getLastName(), medicalRecords);
            logger.info(medicalRecords.getFirstName() + " " + medicalRecords.getLastName() + " is added");
        } catch (Exception e) {
            logger.error("failed to add the MedicalRecords", e);
        }
        return new ArrayList<>(stringMedicalRecordsMap.values());
    }
}
