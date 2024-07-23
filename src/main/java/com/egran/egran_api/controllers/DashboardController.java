package com.egran.egran_api.controllers;

import com.egran.egran_api.dtos.DashboardStats;
import com.egran.egran_api.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<DashboardStats> getDashboardStats(@PathVariable Integer farmerId) {
        DashboardStats dashboardStats = dashboardService.getDashboardStats(farmerId);
        return ResponseEntity.ok(dashboardStats);
    }
}
