package com.egran.egran_api;

import com.egran.egran_api.entities.*;
import com.egran.egran_api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PolygonPointRepository polygonPointRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create and save Admin
        Admin admin = Admin.builder()
                .firstname("Admin One")
                .email("admin1@example.com")
                .password("password")
                .build();
        adminRepository.save(admin);

        // Create and save Farmers
        Farmer farmer1 = Farmer.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .farmLocation("Farm Location 1")
                .admin(admin)
                .build();

        Farmer farmer2 = Farmer.builder()
                .firstname("Jane")
                .lastname("Doe")
                .email("jane.doe@example.com")
                .password("password")
                .farmLocation("Farm Location 2")
                .admin(admin)
                .build();

        farmerRepository.save(farmer1);
        farmerRepository.save(farmer2);

        // Create and save Drones
        Drone drone1 = Drone.builder()
                .model("Model A")
                .build();

        Drone drone2 = Drone.builder()
                .model("Model B")
                .build();

        droneRepository.save(drone1);
        droneRepository.save(drone2);

        // Create and save Flights
        Flight flight1 = Flight.builder()
                .date(LocalDate.now())
                .status(FlightStatus.COMPLETED)
                .startTime(LocalTime.now().minusHours(2))
                .endTime(LocalTime.now().minusHours(1))
                .farmer(farmer1)
                .drone(drone1)
                .build();

        Flight flight2 = Flight.builder()
                .date(LocalDate.now())
                .status(FlightStatus.COMPLETED)
                .startTime(LocalTime.now().minusHours(3))
                .endTime(LocalTime.now().minusHours(2))
                .farmer(farmer2)
                .drone(drone2)
                .build();

        flightRepository.save(flight1);
        flightRepository.save(flight2);

        // Create and save Images
        Image image1 = Image.builder()
                .url("http://example.com/image1.jpg")
                .timestamp(LocalDateTime.now().minusHours(1))
                .lat(34.0522)
                .lng(-118.2437)
                .flight(flight1)
                .build();

        Image image2 = Image.builder()
                .url("http://example.com/image2.jpg")
                .timestamp(LocalDateTime.now().minusHours(1).plusMinutes(10))
                .lat(34.0522)
                .lng(-118.2437)
                .flight(flight1)
                .build();

        Image image3 = Image.builder()
                .url("http://example.com/image3.jpg")
                .timestamp(LocalDateTime.now().minusHours(2))
                .lat(36.7783)
                .lng(-119.4179)
                .flight(flight2)
                .build();

        imageRepository.saveAll(Arrays.asList(image1, image2, image3));

        // Create and save Polygon Points
        PolygonPoint point1 = PolygonPoint.builder()
                .lat(34.0522)
                .lng(-118.2437)
                .sequence(1)
                .flight(flight1)
                .build();

        PolygonPoint point2 = PolygonPoint.builder()
                .lat(34.0523)
                .lng(-118.2438)
                .sequence(2)
                .flight(flight1)
                .build();

        PolygonPoint point3 = PolygonPoint.builder()
                .lat(36.7783)
                .lng(-119.4179)
                .sequence(1)
                .flight(flight2)
                .build();

        PolygonPoint point4 = PolygonPoint.builder()
                .lat(36.7784)
                .lng(-119.4180)
                .sequence(2)
                .flight(flight2)
                .build();

        polygonPointRepository.saveAll(Arrays.asList(point1, point2, point3, point4));
    }
}
