package com.application.safetynet;


import com.application.safetynet.model.Person;
import com.application.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class InMemoryPersonRepositoryTest {

    @Autowired
    PersonRepository inMemoryPersonRepository;

    @Test
    public void initTest() throws IOException {
        inMemoryPersonRepository.init();
       List<Person> personList = inMemoryPersonRepository.findAll();
        Assertions.assertEquals(personList.size(),23);
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
        List<Person> personList= inMemoryPersonRepository.update(updatePerson);
        Assertions.assertEquals(personList.size(),23);
        Assertions.assertTrue(personList.stream().anyMatch(element->element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks") && element.getPhone().equals("0465874525")));
    }

    @Test
    public void findAllTest(){
        List<Person> personList = inMemoryPersonRepository.findAll();
        Assertions.assertNotNull(personList);
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
        List<Person> personList = inMemoryPersonRepository.create(createPerson);
        Assertions.assertEquals(personList.size(),24);
        Assertions.assertTrue(personList.stream().anyMatch(element->element.getFirstName().equals("Aimen") && element.getLastName().equals("Jerbi") && element.getPhone().equals("0465874525")));
    }

    @Test
    public void delete() throws Exception {
        inMemoryPersonRepository.init();
        Person personDelete = new Person();
        personDelete.setFirstName("Zach");
        personDelete.setLastName("Zemicks");
        List<Person> delete = inMemoryPersonRepository.delete(personDelete);
        System.out.println(delete);
        Assertions.assertEquals(delete.size(),22);
        Assertions.assertFalse(delete.stream().anyMatch(element->element.getFirstName().equals("Zach") && element.getLastName().equals("Zemicks")));
    }
}

