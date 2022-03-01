package com.application.safetynet.repository;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.Person;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    void init() throws IOException;

    public List<Person> findAll();

    public Optional<Person> findByStation(String id);

    public void deleteById(String id);

    public Person save(Person person);
}
