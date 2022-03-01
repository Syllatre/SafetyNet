package com.application.safetynet.repository;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationRepository {
    @Bean(initMethod = "init")
    public FireStationRepository fireStationRepository()
    {
        return new InMemoryFireStationRepository();
    }

    @Bean(initMethod = "init")
    public PersonRepository personRepository(){return  new InMemoryPersonRepository();}
}
