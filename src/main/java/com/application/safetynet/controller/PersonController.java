package com.application.safetynet.controller;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.Person;
import com.application.safetynet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/person")
    public Person creationPerson (@RequestBody Person person){
        return personService.savePerson(person);
    }

    @GetMapping("/persons")
    public List<Person> getPersons(){return personService.getPersons();}

    @DeleteMapping("person/{id}")
    public void deletePerson(@PathVariable("id") String id){
        personService.deletePerson(id);
    }
    @PutMapping("/person/{id}")
    public Person updatePerson (@PathVariable("id") final String id, @RequestBody Person person){
        Optional<Person> f =personService.getPerson(id);
        if (f.isPresent()){
            Person currentPerson = f.get();

            String id1 = person.getFirstName()+" "+person.getLastName();
            if(id != null){
            }
                deletePerson(id1);
            currentPerson.setAddress(person.getAddress());
            currentPerson.setCity(person.getCity());
            currentPerson.setZip(person.getZip());
            currentPerson.setPhone(person.getPhone());
            person.setEmail(person.getEmail());

            }
            personService.savePerson(cur);
            return currentFireStation;
        }
        else{
            return null;
        }
    public

}
