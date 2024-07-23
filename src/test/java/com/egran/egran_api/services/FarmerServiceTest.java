package com.egran.egran_api.services;

import com.egran.egran_api.dtos.FarmerDto;
import com.egran.egran_api.entities.Admin;
import com.egran.egran_api.entities.Farmer;
import com.egran.egran_api.repositories.FarmerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FarmerServiceTest {

    @Mock
    private FarmerRepository farmerRepository;

    @InjectMocks
    private FarmerService farmerService;

    private Farmer farmer;
    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        admin.setId(1);

        farmer = Farmer.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .farmLocation("FarmLocation")
                .admin(admin)
                .build();
    }

    @Test
    void testGetFarmerById() {
        when(farmerRepository.findById(1)).thenReturn(Optional.of(farmer));

        FarmerDto result = farmerService.getFarmerById(1);

        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(1, result.getAdminId());
    }

    @Test
    void testGetFarmersByAdminId() {
        when(farmerRepository.findByAdminId(1)).thenReturn(Arrays.asList(farmer));

        List<FarmerDto> result = farmerService.getFarmersByAdminId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstname());
        assertEquals("Doe", result.get(0).getLastname());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
        assertEquals(1, result.get(0).getAdminId());
    }
}
