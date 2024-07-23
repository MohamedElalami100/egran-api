package com.egran.egran_api.services;

import com.egran.egran_api.dtos.CreateFarmerDto;
import com.egran.egran_api.dtos.FarmerDto;
import com.egran.egran_api.entities.Admin;
import com.egran.egran_api.entities.Farmer;
import com.egran.egran_api.exceptions.ResourceNotFoundException;
import com.egran.egran_api.repositories.AdminRepository;
import com.egran.egran_api.repositories.FarmerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FarmerService {
    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private AdminRepository adminRepository;

    public FarmerDto getFarmerById(Integer farmerId){
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new ResourceNotFoundException("farmer not found with id " + farmerId));

        //get adminId
        Integer adminId = null;
        if (farmer.getAdmin() != null)
            adminId = farmer.getAdmin().getId();

        return FarmerDto.builder()
                .id(farmer.getId())
                .email(farmer.getEmail())
                .farmLocation(farmer.getFarmLocation())
                .firstname(farmer.getFirstname())
                .lastname(farmer.getLastname())
                .email(farmer.getEmail())
                .adminId(adminId)
                .build();
    }

    public List<FarmerDto> getFarmersByAdminId(Integer adminId){
        List<Farmer> farmers = farmerRepository.findByAdminId(adminId);

        List<FarmerDto> farmerDtos = new ArrayList<>();
        for (Farmer farmer: farmers) {
            farmerDtos.add(FarmerDto.builder()
                    .id(farmer.getId())
                    .email(farmer.getEmail())
                    .farmLocation(farmer.getFarmLocation())
                    .firstname(farmer.getFirstname())
                    .lastname(farmer.getLastname())
                    .email(farmer.getEmail())
                    .adminId(adminId)
                    .build());
        }
        return farmerDtos;
    }

    public FarmerDto createFarmer(CreateFarmerDto farmerDto) {
        Admin admin = adminRepository.findById(farmerDto.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id " + farmerDto.getAdminId()));

        Farmer farmer = Farmer.builder()
                .firstname(farmerDto.getFirstname())
                .lastname(farmerDto.getLastname())
                .email(farmerDto.getEmail())
                .password(farmerDto.getPassword())
                .farmLocation(farmerDto.getFarmLocation())
                .admin(admin)
                .build();

        farmer = farmerRepository.save(farmer);

        return FarmerDto.builder()
                .id(farmer.getId())
                .firstname(farmer.getFirstname())
                .lastname(farmer.getLastname())
                .email(farmer.getEmail())
                .farmLocation(farmer.getFarmLocation())
                .adminId(admin.getId())
                .build();
    }

    public CreateFarmerDto updateFarmer(Integer id, CreateFarmerDto farmerDto) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with id " + id));

        if (farmerDto.getFirstname() != null)
            farmer.setFirstname(farmerDto.getFirstname());
        if (farmerDto.getLastname() != null)
            farmer.setLastname(farmerDto.getLastname());
        if (farmerDto.getEmail() != null)
            farmer.setEmail(farmerDto.getEmail());
        if (farmerDto.getPassword() != null)
            farmer.setPassword(farmerDto.getPassword());
        if (farmerDto.getFarmLocation() != null)
            farmer.setFarmLocation(farmerDto.getFarmLocation());

        farmer = farmerRepository.save(farmer);

        return CreateFarmerDto.builder()
                .id(farmer.getId())
                .firstname(farmer.getFirstname())
                .lastname(farmer.getLastname())
                .email(farmer.getEmail())
                .farmLocation(farmer.getFarmLocation())
                .build();
    }

    public void deleteFarmer(Integer id) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with id " + id));

        farmerRepository.delete(farmer);
    }

}
