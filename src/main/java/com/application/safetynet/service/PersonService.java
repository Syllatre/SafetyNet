package com.application.safetynet.service;


import com.application.safetynet.model.Person;
import com.application.safetynet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> getPersons(){
        return personRepository.findAll();
    }
    public List<Person> createPerson(Person person){
        return personRepository.create(person);
    }
    public List<Person> delete (Person personDelete){
        return personRepository.delete(personDelete);
    }
    public List<Person> update (Person personUpdate){
        return personRepository.update(personUpdate);
    }
}
