package com.application.safetynet.service;

import com.application.safetynet.model.FireStation;
import com.application.safetynet.repository.FireStationRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    FireStationRepository fireStationRepository;

    @InjectMocks
    FireStationService fireStationService;

    private static final List<FireStation> fireStationList = List.of(FireStation.builder().station("1").addresses(List.of("908 73rd St","947 E. Rose Dr","644 Gershwin Cir")).build(),
            FireStation.builder().station("2").addresses(List.of("29 15th St","892 Downing Ct","951 LoneTree Rd")).build(),
            FireStation.builder().station("3").addresses(List.of("834 Binoc Ave","748 Townings Dr","112 Steppes Pl")).build(),
            FireStation.builder().station("4").addresses(List.of("112 Steppes Pl","489 Manchester St")).build());

    @Test
    void getAddressByStationNumberTest() throws IOException {
        when(fireStationRepository.findAll()).thenReturn(fireStationList);
        List<String> getAddressByStationNumber = fireStationService.getAddressByStationNumber(2);
        assertEquals(getAddressByStationNumber.size(),3);
        assertTrue(getAddressByStationNumber.contains("29 15th St"));
        System.out.println(getAddressByStationNumber);

    }

//    public List<String> getStationByAddress (String address){
//        List<FireStation> fireStationsList = fireStationRepository.findAll();
//        return fireStationsList
//                .stream()
//                .filter(station-> station.getAddresses().contains(address))
//                .map(station-> station.getStation()).collect(Collectors.toList());
//    }

    @Test
    void getStationByAddressTest() throws IOException {
        when(fireStationRepository.findAll()).thenReturn(fireStationList);
        List<String> getStationByAddress = fireStationService.getStationByAddress("112 Steppes Pl");
        assertEquals(getStationByAddress.size(),2);
        assertTrue(getStationByAddress.contains("3"));
        assertTrue(getStationByAddress.contains("4"));

    }
}
