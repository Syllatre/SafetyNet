package com.application.safetynet.repository;


import com.application.safetynet.model.MedicalRecord;
import com.application.safetynet.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InMemoryPersonRepositoryTest {


    PersonRepository inMemoryPersonRepository = new InMemoryPersonRepository();

    @Test
    public void initTest() throws IOException {
        inMemoryPersonRepository.init();
        List<Person> personList = inMemoryPersonRepository.findAll();
        Assertions.assertEquals(personList.size(), 23);
        Assertions.assertNotNull(personList);
    }

    @Test
    public void update() throws IOException {
        inMemoryPersonRepository.init();
        Person updatePerson = new Person();
        updatePerson.setFirstName("Zach");
        updatePerson.setLastName("Zemicks");
        updatePerson.setAddress("10 avenue des oiseaux");
        updatePerson.setCity("Paris");
        updatePerson.setEmail("aimen@gmail.com");
        updatePerson.setPhone("0465874525");
        updatePerson.setZip("75013");
        Person person = inMemoryPersonRepository.update(updatePerson);
        List<Person> result = inMemoryPersonRepository.findAll();
        Assertions.assertEquals(result.size(), 23);
        Assertions.assertTrue(result.stream().anyMatch(element -> element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks") && element.getPhone().equals("0465874525")));
    }

    @Test
    public void findAllTest() throws IOException {
        inMemoryPersonRepository.init();
        List<Person> personList = inMemoryPersonRepository.findAll();
        Assertions.assertNotNull(personList);
        Assertions.assertEquals(personList.size(), 23);
    }

    @Test
    public void create() throws IOException {
        inMemoryPersonRepository.init();
        Person createPerson = new Person();
        createPerson.setFirstName("Aimen");
        createPerson.setLastName("Jerbi");
        createPerson.setAddress("10 avenue des oiseaux");
        createPerson.setCity("Paris");
        createPerson.setEmail("aimen@gmail.com");
        createPerson.setPhone("0465874525");
        createPerson.setZip("75013");
        Person person = inMemoryPersonRepository.create(createPerson);
        List<Person> result = inMemoryPersonRepository.findAll();
        Assertions.assertTrue(result.stream().anyMatch(element -> element.getFirstName().equals("Aimen") && element.getLastName().equals("Jerbi") && element.getPhone().equals("0465874525")));
    }

    @Test
    public void deleteIfTrue() throws Exception {
        inMemoryPersonRepository.init();
        Person personDelete = new Person();
        personDelete.setFirstName("Zach");
        personDelete.setLastName("Zemicks");
        inMemoryPersonRepository.delete(personDelete);
        List<Person> refreshList = new ArrayList<>(inMemoryPersonRepository.findAll());
        Assertions.assertEquals(refreshList.size(), 22);
        Assertions.assertFalse(refreshList.stream().anyMatch(element -> element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks")));
    }

    @Test
    public void deleteIfFalse() throws Exception {
        inMemoryPersonRepository.init();
        Person personDelete = new Person();
        personDelete.setFirstName("");
        personDelete.setLastName("Zemicks");
        inMemoryPersonRepository.delete(personDelete);
        List<Person> refreshList = new ArrayList<>(inMemoryPersonRepository.findAll());
        Assertions.assertEquals(refreshList.size(), 23);
    }
}

