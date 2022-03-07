package com.application.safetynet.controller;

import com.application.safetynet.model.Person;
import com.application.safetynet.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class PersonController {
    private static Logger logger = LoggerFactory.getLogger(PersonController .class);

    @Autowired
    PersonService personService;

    @GetMapping("/persons")
    List<Person> getPersons (){
        logger.info("All Person http://localhost:8080/person");
        return personService.getPersons();
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestBody Person personDelete){
        logger.info("DELETE http://localhost:8080/person");
        logger.info("body: " + personDelete);
        personService.delete(personDelete);
    }

    @PostMapping("/person")
    public List<Person> addPerson (@RequestBody Person person){
        logger.info("POST http://localhost:8080/person");
        logger.info("body: " + person);
        return personService.createPerson(person);
    }
    @PutMapping("/person")
    List<Person> updatePerson (@RequestBody Person personUpdate){
        logger.info("PUT http://localhost:8080/person");
        logger.info("body: " + personUpdate);
        return personService.update(personUpdate);
    }
}
