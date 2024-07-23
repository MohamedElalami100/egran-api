package com.egran.egran_api.dtos;

import com.egran.egran_api.entities.Farmer;
import com.egran.egran_api.entities.FlightStatus;
import com.egran.egran_api.entities.Image;
import com.egran.egran_api.entities.PolygonPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDetailsDto {
    private Integer id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration duration;
    private FlightStatus status;
    private Integer farmerId;
    private List<Image> images;
    private List<PolygonPoint> polygonPoints;
}
