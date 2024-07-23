package com.egran.egran_api.repositories;

import com.egran.egran_api.entities.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Integer> {
    List<Farmer> findByAdminId(Integer adminId);

}

