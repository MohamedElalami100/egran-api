package com.egran.egran_api.services;

import com.egran.egran_api.dtos.FlightDetailsDto;
import com.egran.egran_api.dtos.FlightReducedDto;
import com.egran.egran_api.dtos.ImageDto;
import com.egran.egran_api.dtos.PolygonPointDto;
import com.egran.egran_api.entities.*;
import com.egran.egran_api.exceptions.ResourceNotFoundException;
import com.egran.egran_api.repositories.FlightRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
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

        List<Image> images = new ArrayList<>();
        if (flight.getImages() != null) {
            images = flight.getImages();
        }

        // Map to group images by pairId
        List<List<ImageDto>> groupedImages = getImageDtos(images);

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
                .altitude(flight.getAltitude())
                .area(flight.getArea())
                .status(flight.getStatus())
                .startTime(flight.getStartTime())
                .endTime(flight.getEndTime())
                .duration(duration)
                .farmerId(farmerId)
                .images(groupedImages)
                .polygonPoints(polygonPoints)
                .build();
    }

    public List<FlightDetailsDto> getInProgressFlight(Integer adminId){
        List<Flight> flights = flightRepository.findByStatusAndFarmerAdminId(FlightStatus.IN_PROGRESS,
                adminId);
        if(flights.size() == 0)
            return new ArrayList<>();

        Flight flight = flights.get(0);
        List<FlightPoint> flightPoints = new ArrayList<>();
        if (flight.getFlightPoints() != null) {
            flightPoints = flight.getFlightPoints();
        }

        // Map to group images by pairId
        List<ImageDto> images = new ArrayList<>();

        for (FlightPoint flightPoint: flightPoints) {
            ImageDto imageDto = ImageDto.builder()
                    .lat(flightPoint.getLat())
                    .lng(flightPoint.getLng())
                    .build();
            images.add(imageDto);
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

        List<List<ImageDto>> groupedImages = new ArrayList<>();
        groupedImages.add(images);

        FlightDetailsDto flightDetailsDto = FlightDetailsDto.builder()
                .id(flight.getId())
                .date(flight.getDate())
                .altitude(flight.getAltitude())
                .status(flight.getStatus())
                .startTime(flight.getStartTime())
                .endTime(flight.getEndTime())
                .duration(duration)
                .area(flight.getArea())
                .predictedDuration(flight.getPredictedDuration())
                .farmerId(farmerId)
                .images(groupedImages)
                .polygonPoints(polygonPoints)
                .build();

        List<FlightDetailsDto> flightDetailsDtos = new ArrayList<>();
        flightDetailsDtos.add(flightDetailsDto);
        return flightDetailsDtos;
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
                        .altitude(flight.getAltitude())
                        .area(flight.getArea())
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
                    .altitude(flight.getAltitude())
                    .area(flight.getArea())
                    .farmerId(farmerId)
                    .build());
        }

        return flightReducedDtos;
    }

    private static List<List<ImageDto>> getImageDtos(List<Image> images) {
        List<List<ImageDto>> imageLists = new ArrayList<>();

        for (Image image : images) {
            ImageDto imageDto = ImageDto.builder()
                    .id(image.getId())
                    .url(image.getUrl())
                    .timestamp(image.getTimestamp())
                    .type(image.getType())
                    .lat(image.getLat())
                    .lng(image.getLng())
                    .aiInsight(image.getAiInsight())
                    .tutaCount(image.getTutaCount())
                    .oidiumCount(image.getOidiumCount())
                    .build();

            boolean pairFound = false;

            // Check if there's already a list for this pairId
            for (List<ImageDto> pairList : imageLists) {
                if (!pairList.isEmpty() && pairList.get(0).getTimestamp().equals(image.getTimestamp())) {
                    pairList.add(imageDto);
                    pairFound = true;
                }
            }

            // If no list was found for this pairId, create a new one
            if (!pairFound) {
                List<ImageDto> newPairList = new ArrayList<>();
                newPairList.add(imageDto);
                imageLists.add(newPairList);
            }
        }

        return imageLists;
    }


}
