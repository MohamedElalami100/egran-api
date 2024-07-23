package com.egran.egran_api.repositories;

import com.egran.egran_api.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    List<Flight> findByFarmerId(Integer farmerId);
    List<Flight> findByFarmerAdminId(Integer adminId);
    Integer countByFarmerId(Integer farmerId);

}
