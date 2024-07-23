package com.egran.egran_api.dtos;

import com.egran.egran_api.entities.Farmer;
import com.egran.egran_api.entities.FlightStatus;
import com.egran.egran_api.entities.Image;
import com.egran.egran_api.entities.PolygonPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightReducedDto {
    private Integer id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration duration;
    private FlightStatus status;
    private Integer farmerId;
}
