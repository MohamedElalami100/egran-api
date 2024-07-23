package com.egran.egran_api.dtos;

import com.egran.egran_api.entities.Admin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmerDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String farmLocation;
    private Integer adminId;
}
