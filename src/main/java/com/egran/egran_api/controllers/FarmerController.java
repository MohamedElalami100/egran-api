package com.egran.egran_api.controllers;

import com.egran.egran_api.dtos.CreateFarmerDto;
import com.egran.egran_api.dtos.FarmerDto;
import com.egran.egran_api.services.FarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {

    private final FarmerService farmerService;

    @PostMapping
    public ResponseEntity<FarmerDto> createFarmer(@RequestBody CreateFarmerDto farmerDto) {
        FarmerDto createdFarmer = farmerService.createFarmer(farmerDto);
        return ResponseEntity.ok(createdFarmer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateFarmerDto> updateFarmer(@PathVariable Integer id, @RequestBody CreateFarmerDto farmerDto) {
        CreateFarmerDto updatedFarmer = farmerService.updateFarmer(id, farmerDto);
        return ResponseEntity.ok(updatedFarmer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable Integer id) {
        farmerService.deleteFarmer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{farmerId}")
    public ResponseEntity<FarmerDto> getFarmerById(@PathVariable Integer farmerId) {
        FarmerDto farmer = farmerService.getFarmerById(farmerId);
        return ResponseEntity.ok(farmer);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<FarmerDto>> getFarmersByAdminId(@PathVariable Integer adminId) {
        List<FarmerDto> farmers = farmerService.getFarmersByAdminId(adminId);
        return ResponseEntity.ok(farmers);
    }
}
