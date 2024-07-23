package com.egran.egran_api.services;

import com.egran.egran_api.dtos.FlightDetailsDto;
import com.egran.egran_api.dtos.FlightReducedDto;
import com.egran.egran_api.dtos.ImageDto;
import com.egran.egran_api.dtos.PolygonPointDto;
import com.egran.egran_api.entities.Flight;
import com.egran.egran_api.entities.Image;
import com.egran.egran_api.entities.PolygonPoint;
import com.egran.egran_api.exceptions.ResourceNotFoundException;
import com.egran.egran_api.repositories.FlightRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public FlightDetailsDto getFlightById(Integer flightId){
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("flight not found with id " + flightId));

        //get Images:
        List<Image> images = new ArrayList<>();
        if (flight.getImages() != null)
            images = flight.getImages();
        List<ImageDto> imageDtos = new ArrayList<>();
        for (Image image: images) {
            imageDtos.add(ImageDto.builder()
                            .id(image.getId())
                            .url(image.getUrl())
                            .timestamp(image.getTimestamp())
                            .lat(image.getLat())
                            .lng(image.getLng())
                    .build());
        }

        //get polygon points:
        List<PolygonPoint> polygonPoints = new ArrayList<>();
        if (flight.getPolygonPoints() != null)
            polygonPoints = flight.getPolygonPoints().stream()
                    .filter(p -> p.getSequence() != null)
                    .sorted(Comparator.comparingInt(PolygonPoint::getSequence))
                    .collect(Collectors.toList());
        List<PolygonPointDto> polygonPointDtos = new ArrayList<>();
        for (PolygonPoint polygonPoint: polygonPoints) {
            polygonPointDtos.add(PolygonPointDto.builder()
                            .id(polygonPoint.getId())
                            .lat(polygonPoint.getLat())
                            .lng(polygonPoint.getLng())
                    .build());
        }

        //get duration:
        Duration duration = null;
        if (flight.getEndTime() != null && flight.getStartTime() != null)
            duration = Duration.between(flight.getStartTime(), flight.getEndTime());

        //get farmerId:
        Integer farmerId = null;
        if (flight.getFarmer() != null)
            farmerId = flight.getFarmer().getId();

        return FlightDetailsDto.builder()
                .id(flight.getId())
                .date(flight.getDate())
                .status(flight.getStatus())
                .startTime(flight.getStartTime())
                .endTime(flight.getEndTime())
                .duration(duration)
                .farmerId(farmerId)
                .images(images)
                .polygonPoints(polygonPoints)
                .build();
    }

    public List<FlightReducedDto> getFlightsByFarmerId(Integer farmerId){
        List<Flight> flights = flightRepository.findByFarmerId(farmerId);

        List<FlightReducedDto> flightReducedDtos = new ArrayList<>();
        for (Flight flight: flights) {
            //get duration:
            Duration duration = null;
            if (flight.getEndTime() != null && flight.getStartTime() != null)
                duration = Duration.between(flight.getStartTime(), flight.getEndTime());

            flightReducedDtos.add(FlightReducedDto.builder()
                        .id(flight.getId())
                        .date(flight.getDate())
                        .status(flight.getStatus())
                        .startTime(flight.getStartTime())
                        .endTime(flight.getEndTime())
                        .duration(duration)
                        .farmerId(farmerId)
                    .build());
        }

        return flightReducedDtos;
    }

    public List<FlightReducedDto> getFlightsByAdminId(Integer adminId){
        List<Flight> flights = flightRepository.findByFarmerAdminId(adminId);

        List<FlightReducedDto> flightReducedDtos = new ArrayList<>();
        for (Flight flight: flights) {
            //get duration:
            Duration duration = null;
            if (flight.getEndTime() != null && flight.getStartTime() != null)
                duration = Duration.between(flight.getStartTime(), flight.getEndTime());

            //get farmerId:
            Integer farmerId = null;
            if (flight.getFarmer() != null)
                farmerId = flight.getFarmer().getId();

            flightReducedDtos.add(FlightReducedDto.builder()
                    .id(flight.getId())
                    .date(flight.getDate())
                    .status(flight.getStatus())
                    .startTime(flight.getStartTime())
                    .endTime(flight.getEndTime())
                    .duration(duration)
                    .farmerId(farmerId)
                    .build());
        }

        return flightReducedDtos;
    }




}
