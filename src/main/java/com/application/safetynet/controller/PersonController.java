package com.application.safetynet.controller;

import com.application.safetynet.exceptions.AddressNotExistException;
import com.application.safetynet.exceptions.CityNotExistException;
import com.application.safetynet.exceptions.FireStationNotExistException;
import com.application.safetynet.exceptions.PersonNotExistException;
import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.ChildAlertDto;
import com.application.safetynet.model.dto.PersonEmail;
import com.application.safetynet.model.dto.PersonWithMedicalAndEmailDto;
import com.application.safetynet.model.dto.PhoneAlert;
import com.application.safetynet.repository.FireStationRepository;
import com.application.safetynet.repository.PersonRepository;
import com.application.safetynet.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FireStationRepository fireStationRepository;

    /**
     * Get child with his Family list by address
     *
     * @param address address to filter result
     * @return list of child with family list in this address
     */
    //http://localhost:8080/childAlert?address=<address>
    @GetMapping("childAlert")
    public ChildAlertDto getChildByAddressWithFamily(@RequestParam(name = "address") final String address) throws AddressNotExistException {
        boolean addressExist = personRepository.findAll().stream().anyMatch(element -> element.getAddress().equalsIgnoreCase(address));
        if (!addressExist) throw new AddressNotExistException("L'adresse "+address+" que vous avez saisie n'existe pas dans la base de donnée");
        logger.info("List of child and family by address {}", address);
        return personService.getChildAndFamilyByAddress(address);
    }

    /**
     * Get list of persons attached to the station mentioned
     *
     * @param id firestation number
     * @return List of phone by station number
     */
    //http://localhost:8080/phoneAlert?firestation=<firestation_number>
    @GetMapping("phoneAlert")
    public List<PhoneAlert> getPersonPhoneByStation(@RequestParam(name = "firestation") final int id) throws IOException {
        boolean stationExist = fireStationRepository.findAll().stream().anyMatch(station -> Integer.parseInt(station.getStation()) == id);
        if (!stationExist) throw new FireStationNotExistException("La station "+id+" que vous avez saisie n'existe pas");
        logger.info("List of phone by station number {}", id);
        return personService.getPersonPhoneByStation(id);
    }

    /**
     * Get persons with medical records, age and email
     *
     * @param firstName first name of person to find
     * @param lastName  last name of person to find
     * @return persons with medical records, age and email
     */
    //http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    @GetMapping("personInfo")
    public List<PersonWithMedicalAndEmailDto> getPersonWithMedicalAndEmail(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) throws PersonNotExistException {
        boolean personExist = personRepository.findAll().stream().anyMatch(element -> element.getFirstName().equalsIgnoreCase(firstName)&& element.getLastName().equalsIgnoreCase(lastName));
        if (!personExist) throw new PersonNotExistException("La personne : "+firstName+" "+lastName+ " que vous avez saisie n'existe pas dans notre base de donnée");
        logger.info("persons with medical records, age and email");
        return personService.getPersonWithMedicalAndEmail(firstName, lastName);
    }

    /**
     * Get child with his Family list by address
     *
     * @param city city where persons live
     * @return list of email
     */
    //http://localhost:8080/communityEmail?city=<city>
    @GetMapping("communityEmail")
    public List<PersonEmail> getPersonEmail(@RequestParam(name = "city") String city) throws CityNotExistException {
        boolean cityExist = personRepository.findAll().stream().anyMatch(element -> element.getCity().equalsIgnoreCase(city));
        if (!cityExist) throw new CityNotExistException("La ville"+city+" que vous avez saisie n'existe pas dans notre base de donnée");
        logger.info("List of email");
        return personService.getPersonEmail(city);
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestBody Person personDelete) {
        logger.info("Deleting person {}", personDelete);
        personService.delete(personDelete);
    }

    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        logger.info("Creating person{}", person);
        return personService.createPerson(person);
    }

    @PutMapping("/person")
    Person updatePerson(@RequestBody Person personUpdate) {
        logger.info("Updating person {}", personUpdate);
        return personService.update(personUpdate);
    }
}
