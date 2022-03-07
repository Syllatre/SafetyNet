package com.application.safetynet.repository;


import com.application.safetynet.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    void init() throws IOException;

    public List<Person> findAll();

    public ArrayList<Person> delete(Person personDelete);

    public List<Person> update(Person personUpdate);

    public List<Person> create(Person person);
}
