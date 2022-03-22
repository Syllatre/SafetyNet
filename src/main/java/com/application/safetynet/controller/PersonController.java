package com.application.safetynet.controller;

import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.PersonWithMedicalRecordAndAgeDto;
import com.application.safetynet.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    PersonService personService;

    @GetMapping("/person/{id}")
    public Map<String,Object> getAdultAndChildInStation(@PathVariable final int id) throws IOException {
        return personService.countAdultAndChild(id);
    }

    @GetMapping("person/family_by_address/{address}")
    public Map<String, Object> getFamilyByAddress(@PathVariable final String address){
        return personService.getChildAndFamilyByAddress(address);
    }

    @GetMapping("person/person_with_medicalrecord/{address}")
    public Map<String,List<PersonWithMedicalRecordAndAgeDto>> getPersonAndMedicalRecordPerAddress(@PathVariable final String address){
        return personService.getPersonAndMedicalRecordPerAddress(address);
    }

    @GetMapping("person/phone_by_station/{id}")
    public Map<String,List<String>> getFamilyByAddress(@PathVariable final int id) throws IOException {
        return personService.getPersonPhoneByStation(id);
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestBody Person personDelete) {
        logger.info("DELETE http://localhost:8080/person");
        logger.info("body: " + personDelete);
        personService.delete(personDelete);
    }

    @PostMapping("/person")
    public List<Person> addPerson(@RequestBody Person person) {
        logger.info("POST http://localhost:8080/person");
        logger.info("body: " + person);
        return personService.createPerson(person);
    }

    @PutMapping("/person")
    List<Person> updatePerson(@RequestBody Person personUpdate) {
        logger.info("PUT http://localhost:8080/person");
        logger.info("body: " + personUpdate);
        return personService.update(personUpdate);
    }
}
