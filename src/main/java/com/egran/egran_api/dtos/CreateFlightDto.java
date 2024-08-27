package com.egran.egran_api.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFlightDto {
    private int farmerId;
    private int droneId;
    private LocalDate date;
    private LocalTime startTime;
    private List<CreatePolygonPointDto> polygonPointDtoList;
    private List<CreatePolygonPointDto> flightPoints;
}
