package com.egran.egran_api.services;

import com.egran.egran_api.dtos.CreateFlightDto;
import com.egran.egran_api.dtos.CreatePolygonPointDto;
import com.egran.egran_api.entities.*;
import com.egran.egran_api.exceptions.ResourceNotFoundException;
import com.egran.egran_api.repositories.*;
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

    @Autowired
    private FlightPointRepository flightPointRepository;

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
                .isDroneConnected(false)
                .altitude(createFlightDto.getAltitude())
                .drone(drone)
                .farmer(farmer)
                .build();

        flight = flightRepository.save(flight);

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

        sequence = 0;
        for (CreatePolygonPointDto polygonPointDto : createFlightDto.getFlightPoints()) {
            FlightPoint flightPoint = FlightPoint.builder()
                    .lng(polygonPointDto.getLng())
                    .lat(polygonPointDto.getLat())
                    .sequence(sequence)
                    .flight(flight)
                    .build();
            sequence++;
            flightPointRepository.save(flightPoint);
        }

        return flight;
    }

    public boolean checkDroneConnexion(Integer flightId){
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("flight not found with id " + flightId));
        return flight.getIsDroneConnected();
    }

    public void deleteFlightById(Integer flightId) {
        // Find the flight by id
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + flightId));

        // Delete all polygon points associated with the flight
        polygonPointRepository.deleteByFlight(flight);

        // Delete all flight points associated with the flight
        flightPointRepository.deleteByFlight(flight);

        // Finally, delete the flight
        flightRepository.delete(flight);
    }
}
