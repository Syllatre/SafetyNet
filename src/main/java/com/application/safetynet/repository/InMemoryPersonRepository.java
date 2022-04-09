package com.application.safetynet.repository;

import com.application.safetynet.model.Person;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryPersonRepository implements PersonRepository {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryPersonRepository.class);

    private final Map<String, Person> stringPersonMap = new HashMap<>();


    @PostConstruct
    public void init() throws IOException {
        String content = null;
        try {
            logger.debug(" Person data initialized");
            File file = ResourceUtils.getFile("classpath:data.json");
            content = Files.readString(file.toPath());
        } catch (IOException e) {
            logger.error("failed to load person data", e);
        }
        assert content != null;
        Any fireStationsAny = JsonIterator.deserialize(content).get("persons", '*');
        fireStationsAny.forEach(element -> {
            String firstName = element.get("firstName").toString();
            String lastName = element.get("lastName").toString();
            String address = element.get("address").toString();
            String city = element.get("city").toString();
            String zip = element.get("zip").toString();
            String phone = element.get("phone").toString();
            String email = element.get("email").toString();
            String id = firstName + " " + lastName;
            stringPersonMap.put(id, new Person(firstName, lastName, address, city, zip, phone, email));
        });
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(stringPersonMap.values());
    }


    @Override
    public void delete(Person personDelete) {
        boolean deleted = stringPersonMap.values().removeIf(person -> personDelete.getFirstName().equalsIgnoreCase(person.getFirstName())
                && personDelete.getLastName().equalsIgnoreCase(person.getLastName()));
        if (deleted) {
            logger.debug(personDelete.getFirstName() + " " + personDelete.getLastName() + " is delete");
            logger.debug("now there is " + stringPersonMap.size() + " persons");
        } else {
            logger.error("nobody knows as" + personDelete.getFirstName() + " " + personDelete.getLastName());
        }
    }

    @Override
    public Person update(Person personUpdate) {
        for (Person person : stringPersonMap.values()) {
            if (personUpdate.getFirstName().equalsIgnoreCase(person.getFirstName())
                    && personUpdate.getFirstName().equalsIgnoreCase(person.getFirstName())) {
                person.setCity(personUpdate.getCity());
                person.setEmail(personUpdate.getEmail());
                person.setPhone(personUpdate.getPhone());
                person.setZip(personUpdate.getZip());
                logger.debug(personUpdate.getFirstName() + " " + personUpdate.getLastName()+"is update");
            }
        }
        return personUpdate;
    }

    @Override
    public Person create(Person person) {
        logger.info(person.getFirstName() + " " + person.getLastName());
        try {
            stringPersonMap.put(person.getFirstName() + " " + person.getLastName(), person);
            logger.debug(person.getFirstName() + " " + person.getLastName() + " is added");
        } catch (Exception e) {
            logger.error("failed to add the person", e);
        }
        return person;
    }
}
