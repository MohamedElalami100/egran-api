package com.egran.egran_api.services;

import com.egran.egran_api.dtos.DashboardStats;
import com.egran.egran_api.entities.Flight;
import com.egran.egran_api.entities.Drone;
import com.egran.egran_api.repositories.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private DashboardService dashboardService;

    private Flight flight1;
    private Flight flight2;
    private Drone drone;

    @BeforeEach
    void setUp() {
        drone = new Drone();
        drone.setId(1);

        flight1 = Flight.builder()
                .id(1)
                .startTime(LocalTime.now().minusHours(2))
                .endTime(LocalTime.now().minusHours(1))
                .drone(drone)
                .build();

        flight2 = Flight.builder()
                .id(2)
                .startTime(LocalTime.now().minusHours(3))
                .endTime(LocalTime.now().minusHours(2))
                .drone(drone)
                .build();
    }

    @Test
    void testGetDashboardStats() {
        when(flightRepository.countByFarmerId(1)).thenReturn(2);
        when(flightRepository.findByFarmerId(1)).thenReturn(Arrays.asList(flight1, flight2));

        DashboardStats result = dashboardService.getDashboardStats(1);

        assertNotNull(result);
        assertEquals(2, result.getTotalFlights());
        assertEquals(2, result.getTotalHours());
        assertEquals(1, result.getTotalDrones());
    }
}
