package com.egran.egran_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolygonPointDto {
    private Integer id;
    private Double lat;
    private Double lng;
}
