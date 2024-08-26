package com.egran.egran_api.controllers;

import com.egran.egran_api.dtos.*;
import com.egran.egran_api.entities.Flight;
import com.egran.egran_api.services.CreateFlightService;
import com.egran.egran_api.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    private final CreateFlightService createFlightService;

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDetailsDto> getFlightById(@PathVariable Integer flightId) {
        FlightDetailsDto flight = flightService.getFlightById(flightId);
        return ResponseEntity.ok(flight);
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<FlightReducedDto>> getFlightsByFarmerId(@PathVariable Integer farmerId) {
        List<FlightReducedDto> flights = flightService.getFlightsByFarmerId(farmerId);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<FlightReducedDto>> getFlightsByAdminId(@PathVariable Integer adminId) {
        List<FlightReducedDto> flights = flightService.getFlightsByAdminId(adminId);
        return ResponseEntity.ok(flights);
    }

    @PostMapping
    public ResponseEntity<Flight> createFarmer(@RequestBody CreateFlightDto createFlightDto) {
        Flight flight = createFlightService.createOnProcessFlight(createFlightDto);
        return ResponseEntity.ok(flight);
    }
}
