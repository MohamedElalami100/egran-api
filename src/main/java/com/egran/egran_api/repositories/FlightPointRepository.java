package com.egran.egran_api.repositories;

import com.egran.egran_api.entities.FlightPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightPointRepository extends JpaRepository<FlightPoint, Integer> {
}

