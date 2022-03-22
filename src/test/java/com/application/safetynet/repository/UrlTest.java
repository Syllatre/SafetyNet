package com.application.safetynet.repository;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.Person;
import com.application.safetynet.repository.FireStationRepository;
import com.application.safetynet.repository.InMemoryFireStationRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class UrlTest {


Url url = new Url();
FireStationRepository inMemoryFireStationRepository = new InMemoryFireStationRepository();
PersonRepository inMemoryPersonRepository = new InMemoryPersonRepository();

    @Test
    public void getAddressByStationNumberTest() throws IOException {
        inMemoryFireStationRepository.init();

//        List<FireStation> fireStationsList = inMemoryFireStationRepository.findAll();
//        int stationNumber = 2;
//        fireStationsList
//                .stream().
//                filter(station -> Integer.parseInt(station.getStation()) == stationNumber)
//                .flatMap(station-> station.getAddresses().stream()).collect(Collectors.toList());
//        System.out.println( fireStationsList
//                .stream().
//                filter(station -> Integer.parseInt(station.getStation()) == stationNumber)
//                .flatMap(station-> station.getAddresses().stream()).collect(Collectors.toList()));

    }
    @Test
    public void ageCalculation() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.FRANCE);
        String t = "03/17/2020";
        LocalDate today1 = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthdate1 = LocalDate.parse(t,dtf);
        int age = Period.between(birthdate1, today1).getYears();

        System.out.println(age);
//        System.out.println(today1);
//        System.out.println(birthdate1);
    }

}
