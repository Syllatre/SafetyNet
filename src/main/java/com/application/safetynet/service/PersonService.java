package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.Person;
import com.application.safetynet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Optional<Person> getPerson(final String id){
        return personRepository.findByStation(id);}

    public List<Person> getPersons(){
        return personRepository.findAll();
    }

    public void deletePerson(final String id){
        personRepository.deleteById(id);
    }

    public Person savePerson(Person person){
        Person savedPerson = personRepository.save(person);
        return savedPerson;
}

}


