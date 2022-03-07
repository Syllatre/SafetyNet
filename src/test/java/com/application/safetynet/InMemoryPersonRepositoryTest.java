package com.application.safetynet;


import com.application.safetynet.repository.InMemoryPersonRepository;
import com.application.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class InMemoryPersonRepositoryTest {
    @Autowired
    PersonRepository inMemoryPersonRepository;

    @Test
    public void initTest() throws IOException {
        System.out.println( inMemoryPersonRepository.findAll());
//        inMemoryPersonRepository.init();
//        List<Person> personList = inMemoryPersonRepository.findAll();
//        Assertions.assertEquals(personList.size(),23);
//        Assertions.assertNotNull(personList);
    }
}
