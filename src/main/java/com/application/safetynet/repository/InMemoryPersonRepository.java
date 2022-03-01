package com.application.safetynet.repository;

import com.application.safetynet.model.Person;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Repository
public class InMemoryPersonRepository implements PersonRepository{
    private static Logger logger = LoggerFactory.getLogger(InMemoryPersonRepository.class);

    private Map<String,Person> personList = new HashMap<>();

    @Override
    public void init() throws IOException {
        String content = null;
        try {
            logger.info(" Person data initialized");
            File file = ResourceUtils.getFile("classpath:data.json");
            content = Files.readString(file.toPath());
        }catch (IOException e){
            logger.error("failed to load person data", e);
        }
        Any fireStationsAny = JsonIterator.deserialize(content).get("persons",'*');
        fireStationsAny.forEach(element->{
            Person person = new Person();
            person.setFirstName(element.get("firstName").toString());
            person.setLastName(element.get("lastName").toString());
            person.setAddress(element.get("address").toString());
            person.setCity(element.get("city").toString());
            person.setZip(element.get("zip").toString());
            person.setPhone(element.get("phone").toString());
            person.setEmail(element.get("email").toString());
            String id = element.get("firstName").toString() +" "+element.get("lastName").toString();
            personList.put(id,person);

        });
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(personList.values());
    }

    @Override
    public Optional<Person> findByStation(String id) {
        return Optional.ofNullable(id).map(personList::get);
    }


    @Override
    public void deleteById(String id) {
        personList.remove(id);

    }

    @Override
    public Person save(Person person) {
        String id = person.getFirstName()+" "+person.getLastName();
        return personList.put(id,person);
    }
}
