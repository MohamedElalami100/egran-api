package com.egran.egran_api.repositories;

import com.egran.egran_api.entities.Flight;
import com.egran.egran_api.entities.PolygonPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolygonPointRepository extends JpaRepository<PolygonPoint, Integer> {
    void deleteByFlight(Flight flight);
}

