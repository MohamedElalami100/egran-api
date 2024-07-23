package com.egran.egran_api.services;

import com.egran.egran_api.dtos.FlightDetailsDto;
import com.egran.egran_api.dtos.FlightReducedDto;
import com.egran.egran_api.entities.Flight;
import com.egran.egran_api.entities.Farmer;
import com.egran.egran_api.entities.FlightStatus;
import com.egran.egran_api.repositories.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight flight;
    private Farmer farmer;

    @BeforeEach
    void setUp() {
        farmer = new Farmer();
        farmer.setId(1);

        flight = Flight.builder()
                .id(1)
                .date(LocalDate.now())
                .status(FlightStatus.COMPLETED)
                .startTime(LocalTime.now().minusHours(1))
                .endTime(LocalTime.now())
                .farmer(farmer)
                .build();
    }

    @Test
    void testGetFlightById() {
        when(flightRepository.findById(1)).thenReturn(Optional.of(flight));

        FlightDetailsDto result = flightService.getFlightById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(FlightStatus.COMPLETED, result.getStatus());
        assertEquals(1, result.getFarmerId());
        assertEquals(Duration.ofHours(1), result.getDuration());
    }

    @Test
    void testGetFlightsByFarmerId() {
        when(flightRepository.findByFarmerId(1)).thenReturn(Arrays.asList(flight));

        List<FlightReducedDto> result = flightService.getFlightsByFarmerId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(FlightStatus.COMPLETED, result.get(0).getStatus());
        assertEquals(1, result.get(0).getFarmerId());
        assertEquals(Duration.ofHours(1), result.get(0).getDuration());
    }

    @Test
    void testGetFlightsByAdminId() {
        when(flightRepository.findByFarmerAdminId(1)).thenReturn(Arrays.asList(flight));

        List<FlightReducedDto> result = flightService.getFlightsByAdminId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(FlightStatus.COMPLETED, result.get(0).getStatus());
        assertEquals(1, result.get(0).getFarmerId());
        assertEquals(Duration.ofHours(1), result.get(0).getDuration());
    }
}
