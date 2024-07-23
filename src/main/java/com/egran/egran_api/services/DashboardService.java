package com.egran.egran_api.services;

import com.egran.egran_api.dtos.DashboardStats;
import com.egran.egran_api.entities.Flight;
import com.egran.egran_api.repositories.FlightRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DashboardService {
    @Autowired
    private FlightRepository flightRepository;

    public DashboardStats getDashboardStats(Integer farmerId){
        //get total flights
        Integer totalFlights = flightRepository.countByFarmerId(farmerId);

        //get total hours
        Integer totalHours = 0;
        List<Flight> flights = flightRepository.findByFarmerId(farmerId);

        for (Flight flight: flights) {
            if (flight.getStartTime() == null || flight.getEndTime() == null)
                continue;

            Duration duration = Duration.between(flight.getStartTime(), flight.getEndTime());
            totalHours += duration.toHoursPart();
        }

        //get drones used
        Integer totalDrones = 0;
        HashSet<Integer> idsSet = new HashSet<>();

        for (Flight flight: flights) {
            if (flight.getDrone() == null)
                continue;

            if (!idsSet.contains(flight.getDrone().getId())){
                idsSet.add(flight.getDrone().getId());
                totalDrones++;
            }
        }

        return DashboardStats.builder()
                .totalFlights(totalFlights)
                .totalHours(totalHours)
                .totalDrones(totalDrones)
                .build();
    }


}
