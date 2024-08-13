package com.egran.egran_api.services;

import com.egran.egran_api.dtos.DroneDto;
import com.egran.egran_api.entities.Drone;
import com.egran.egran_api.repositories.DroneRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DroneService {
    @Autowired
    private DroneRepository droneRepository;

    public List<DroneDto> getDronesByAdminId(Integer adminId){
        List<Drone> drones = droneRepository.findByAdminId(adminId);

        List<DroneDto> droneDtos = new ArrayList<>();
        for (Drone drone: drones) {
            droneDtos.add(DroneDto.builder()
                    .id(drone.getId())
                    .model(drone.getModel())
                    .serialNumber(drone.getSerialNumber())
                    .build());
        }

        return droneDtos;
    }
}
