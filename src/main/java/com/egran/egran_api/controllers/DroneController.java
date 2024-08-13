package com.egran.egran_api.controllers;

import com.egran.egran_api.dtos.DroneDto;
import com.egran.egran_api.services.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/drones")
@RequiredArgsConstructor
public class DroneController {
    private final DroneService droneService;

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<DroneDto>> getDronesByAdminId(@PathVariable Integer adminId) {
        List<DroneDto> drones = droneService.getDronesByAdminId(adminId);
        return ResponseEntity.ok(drones);
    }
}
