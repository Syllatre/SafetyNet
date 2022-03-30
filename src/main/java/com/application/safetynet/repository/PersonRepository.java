package com.application.safetynet.repository;


import com.application.safetynet.model.Person;

import java.io.IOException;
import java.util.List;

public interface PersonRepository {
    void init() throws IOException;

    List<Person> findAll();

    void delete(Person personDelete);

    Person update(Person personUpdate);

    Person create(Person person);
}
