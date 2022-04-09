package com.application.safetynet.controller;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.model.Person;
import com.application.safetynet.model.dto.*;
import com.application.safetynet.repository.FireStationRepository;
import com.application.safetynet.repository.PersonRepository;
import com.application.safetynet.service.FireStationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @MockBean
    private FireStationRepository fireStationRepository;

    @MockBean
    private PersonRepository personRepository;

    private FireStation fireStation;

    @BeforeEach
    void setUp() {
        String station = "10";
        String address = "rue des tourbillons";
        ArrayList<String> addresses = new ArrayList<>();
        addresses.add(address);
        fireStation = new FireStation(station, addresses);
    }

    static final List<FloodDto> getFamilyByStation =
            List.of(FloodDto.builder().address("834 Binoc Ave").personOfFamily(List.of()).build(),
                    FloodDto.builder().address("112 Steppes Pl").personOfFamily(List.of(FamilyByStationDto.builder().firstName("Ron").lastName("Peters").phone("841-874-8888").age(56).medications(List.of()).allergies(List.of()).build(),
                            FamilyByStationDto.builder().firstName("Allison").lastName("Boyd").phone("841-874-9888").age(57).medications(List.of("aznol:350mg")).allergies(List.of("nillacilan")).build())).build());

    private static final List<FamilyByStationDto> getPersonAndMedicalRecordPerAddress =
            List.of(FamilyByStationDto.builder().firstName("Tony").lastName("Cooper").phone("841-874-6874").age(28).medications(List.of("hydrapermazol:300mg", "dodoxadin:30mg")).allergies(List.of("shellfish")).build(),
                    FamilyByStationDto.builder().firstName("Jamie").lastName("Peters").phone("841-874-8888").age(56).medications(List.of()).allergies(List.of()).build(),
                    FamilyByStationDto.builder().firstName("Tony").lastName("Boyd").phone("841-874-9888").age(57).medications(List.of("aznol:200mg")).allergies(List.of("nillacilan")).build());

    private static final List<FireStation> fireStationList = List.of(FireStation.builder().station("1").addresses(List.of("908 73rd St", "947 E. Rose Dr", "644 Gershwin Cir")).build(),
            FireStation.builder().station("2").addresses(List.of("29 15th St", "892 Downing Ct", "951 LoneTree Rd")).build(),
            FireStation.builder().station("3").addresses(List.of("834 Binoc Ave", "748 Townings Dr", "112 Steppes Pl", "1509 Culver St")).build(),
            FireStation.builder().station("4").addresses(List.of("112 Steppes Pl", "489 Manchester St")).build());

    private static final List<Person> personList = List.of(Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com").build(),
            Person.builder().firstName("Jacob").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6513").email("drk@email.com").build(),
            Person.builder().firstName("Tenley").lastName("Boyd").address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512").email("tenz@email.com").build(),
            Person.builder().firstName("Ron").lastName("Peters").address("112 Steppes Pl").city("Culver").zip("97451").phone("841-874-8888").email("jpeter@email.com").build(),
            Person.builder().firstName("Lily").lastName("Cooper").address("489 Manchester St").city("Culver").zip("97451").phone("841-874-9845").email("lily@email.com").build(),
            Person.builder().firstName("Brian").lastName("Stelzer").address("947 E. Rose Dr").city("Culver").zip("97451").phone("841-874-7784").email("bstel@email.com").build(),
            Person.builder().firstName("Allison").lastName("Boyd").address("112 Steppes Pl").city("Culver").zip("97451").phone("841-874-9888").email("aly@imail.com").build());

    @Test
    void addFireStation() throws Exception {
        FireStationDto inputFireStation = new FireStationDto();
        inputFireStation.setStation("10");
        inputFireStation.setAddress("rue des tourbillons");
        Mockito.when(fireStationService.saveFireStation(fireStation)).thenReturn(fireStation);
        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputFireStation)))
                .andExpect(status().isOk());
    }


    @Test
    void updateFireStationWithExistingStation() throws Exception {
        FireStationDto inputFireStation = new FireStationDto();
        inputFireStation.setStation("8");
        inputFireStation.setAddress("rue des tourbillons");

        Mockito.when(fireStationService.saveFireStation(fireStation)).thenReturn(fireStation);
        Mockito.when(fireStationService.getFireStation("10")).thenReturn(java.util.Optional.ofNullable(fireStation));
        mockMvc.perform(put("/firestation/{station}", 10)
                        .content(new ObjectMapper().writeValueAsString(inputFireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value("8"))
                .andExpect(jsonPath("$.addresses").value("rue des tourbillons"));
    }

    @Test
    void updateFireStationWithNotExistingStation() throws Exception {
        FireStationDto inputFireStation = new FireStationDto();
        inputFireStation.setStation("8");
        inputFireStation.setAddress("rue des tourbillons");

        Mockito.when(fireStationService.getFireStation("10")).thenReturn(java.util.Optional.empty());
        mockMvc.perform(put("/firestation/{station}", 10)
                        .content(new ObjectMapper().writeValueAsString(inputFireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFireStationWithRightData() throws Exception {
        FireStationDto inputFireStation = new FireStationDto();
        inputFireStation.setStation("8");
        inputFireStation.setAddress("rue des tourbillons");
        doNothing().when(fireStationService).deleteFireStations(isA(FireStationDto.class));
        fireStationService.deleteFireStations(inputFireStation);
        mockMvc.perform(delete("/firestation")
                        .content(new ObjectMapper().writeValueAsString(inputFireStation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFireStationWithWrongData() throws Exception {
        FireStationDto inputFireStation = new FireStationDto();
        inputFireStation.setStation("8");
        inputFireStation.setAddress("rue des tourbillons");
        doNothing().when(fireStationService).deleteFireStations(isA(FireStationDto.class));
        fireStationService.deleteFireStations(inputFireStation);
        mockMvc.perform(delete("/firestation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        assertThatThrownBy(() -> {
            throw new Exception("failed to delete the firestation. Exception error is: ");
        }).isInstanceOf(Exception.class);
    }

    @Test
        //http://localhost:8080/firestation?stationNumber=<station_number>
    void getAdultAndChildInStationTest() throws Exception {
        CountChildAndAdultDto countChildAndAdult;
        List<PersonDto> personDtoList = new ArrayList<>();
        PersonDto personDto1 = new PersonDto("Zach", "Zemicks", "892 Downing Ct", "841-874-7512");
        PersonDto personDto2 = new PersonDto("Warren", "Zemicks", "892 Downing Ct", "841-874-7512");
        PersonDto personDto3 = new PersonDto("Sophia", "Zemicks", "892 Downing Ct", "841-874-7878");
        PersonDto personDto4 = new PersonDto("Eric", "Cadigan", "951 LoneTree Rd", "841-874-7458");
        PersonDto personDto5 = new PersonDto("Jonanathan", "Marrack", "29 15th St", "841-874-6513");
        personDtoList.add(personDto1);
        personDtoList.add(personDto2);
        personDtoList.add(personDto3);
        personDtoList.add(personDto4);
        personDtoList.add(personDto5);
        countChildAndAdult = new CountChildAndAdultDto(personDtoList, 1, 4);
        when(fireStationService.countAdultAndChild(2)).thenReturn(countChildAndAdult);
        when(fireStationRepository.findAll()).thenReturn(fireStationList);
        mockMvc.perform(get("/firestation?stationNumber=2", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
        //http://localhost:8080/fire?address=<address>
    void getPersonAndMedicalRecordPerAddressTest() throws Exception {
        List<String> station = new ArrayList<>();
        station.add("3");
        station.add("4");
        FireDto fireDto = new FireDto(getPersonAndMedicalRecordPerAddress,station);
        when(fireStationService.getPersonAndMedicalRecordPerAddress("112 Steppes Pl")).thenReturn(fireDto);
        when(personRepository.findAll()).thenReturn(personList);
        mockMvc.perform(get("/fire?address=112 Steppes Pl", "112 Steppes Pl")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deservedPeople", hasSize(3)))
                .andExpect(jsonPath("$.deservedPeople[0].lastName", is("Cooper")))
                .andExpect(jsonPath("$.stationNumber[0]", is("3")));
    }


    @Test
        //http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    void getFamilyByStationTest() throws Exception {
        List<Integer> station = new ArrayList<>();
        station.add(3);
        when(fireStationService.getFamilyByStation(station)).thenReturn(getFamilyByStation);
        when(fireStationRepository.findAll()).thenReturn(fireStationList);
        mockMvc.perform(get("/flood/stations?stations=3", 3)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].personOfFamily[0].firstName", is("Ron")))
                .andExpect(jsonPath("$.[1].personOfFamily[1].firstName", is("Allison")));
    }
}