package com.egran.egran_api.services;

import com.egran.egran_api.dtos.CreateFlightDto;
import com.egran.egran_api.dtos.CreatePolygonPointDto;
import com.egran.egran_api.entities.*;
import com.egran.egran_api.exceptions.ResourceNotFoundException;
import com.egran.egran_api.repositories.DroneRepository;
import com.egran.egran_api.repositories.FarmerRepository;
import com.egran.egran_api.repositories.FlightRepository;
import com.egran.egran_api.repositories.PolygonPointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateFlightService {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private PolygonPointRepository polygonPointRepository;


    public Flight createOnProcessFlight(CreateFlightDto createFlightDto){
        //get Drone by id
        Drone drone = droneRepository.findById(createFlightDto.getDroneId())
                .orElseThrow(() -> new ResourceNotFoundException("Drone not found with id " +
                        createFlightDto.getDroneId()));

        //get farmer by id
        Farmer farmer = farmerRepository.findById(createFlightDto.getFarmerId())
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with id " +
                        createFlightDto.getFarmerId()));

        Flight flight = Flight.builder()
                .date(createFlightDto.getDate())
                .startTime(createFlightDto.getStartTime())
                .status(FlightStatus.IN_PROGRESS)
                .drone(drone)
                .farmer(farmer)
                .build();

        Integer sequence = 0;
        for (CreatePolygonPointDto polygonPointDto : createFlightDto.getPolygonPointDtoList()) {
            PolygonPoint polygonPoint = PolygonPoint.builder()
                    .lng(polygonPointDto.getLng())
                    .lat(polygonPointDto.getLat())
                    .sequence(sequence)
                    .flight(flight)
                    .build();
            sequence++;
            polygonPointRepository.save(polygonPoint);
        }

        return flight;
    }
}
