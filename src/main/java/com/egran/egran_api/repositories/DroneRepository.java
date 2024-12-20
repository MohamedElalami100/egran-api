package com.egran.egran_api.repositories;

import com.egran.egran_api.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findByAdminId(Integer adminId);
}

