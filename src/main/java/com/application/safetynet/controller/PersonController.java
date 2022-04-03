package com.application.safetynet.controller;

import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.ChildAndFamilyByAddressDto;
import com.application.safetynet.model.dto.FamilyByStationDto;
import com.application.safetynet.model.dto.PersonWithMedicalAndEmailDto;
import com.application.safetynet.model.dto.PersonWithMedicalRecordAndAgeDto;
import com.application.safetynet.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    PersonService personService;

    //http://localhost:8080/childAlert?address=<address>
    @GetMapping("childAlert")
    public ChildAndFamilyByAddressDto getChildByAddressWithFamily(@RequestParam(name="address") final String address){
//        logger.info("List of child and family by address {}",address);
        return personService.getChildAndFamilyByAddress(address);

    }

    //http://localhost:8080/phoneAlert?firestation=<firestation_number>
    @GetMapping("/person/phone/station/{id}")
    public Map<String,Set<String>> getPersonPhoneByStation(@PathVariable final int id) throws IOException {
        logger.info("List of phone by station number {}",id);
        return personService.getPersonPhoneByStation(id);
    }

    //http://localhost:8080/fire?address=<address>
    @GetMapping("/person/medicalrecord/{address}")
    public Map<String,List<PersonWithMedicalRecordAndAgeDto>> getPersonAndMedicalRecordPerAddress(@PathVariable final String address){
        logger.info("List of medical records by address {}",address);
        return personService.getPersonAndMedicalRecordPerAddress(address);
    }

    //http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    @GetMapping("/person/family/station/{id}")
    public Map<String,List<FamilyByStationDto>> getFamilyByStation(@PathVariable final int id) throws IOException {
        logger.info("List of family by station number {}",id);
        return personService.getFamilyByStation(id);
    }

    @GetMapping("/person/medicalrecord&email")
    public Map<String,List<PersonWithMedicalAndEmailDto>> getPersonWithMedicalAndEmail() {
        logger.info("List of persons with medical records, age and email");
        return personService.getPersonWithMedicalAndEmail();
    }

    @GetMapping("/person/email")
    public Set<String> getPersonEmail ()  {
        logger.info("List of email");
        return personService.getPersonEmail();
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestBody Person personDelete) {
        logger.info("Deleting person {}",personDelete);
        personService.delete(personDelete);
    }

    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        logger.info("Creating person{}",person);
        return personService.createPerson(person);
    }

    @PutMapping("/person")
        Person updatePerson(@RequestBody Person personUpdate) {
        logger.info("Updating person {}", personUpdate);
        return personService.update(personUpdate);
    }
}
