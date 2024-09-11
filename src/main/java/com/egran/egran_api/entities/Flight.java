package com.egran.egran_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "flight")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FlightStatus status;

    @Column(name = "isDroneConnected")
    private Boolean isDroneConnected;

    @Column(name = "altitude")
    private Integer altitude;

    @Column(name = "area")
    private Double area;

    @Column(name = "predictedDuration")
    private Integer predictedDuration;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;

    @OneToMany(mappedBy = "flight",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "flight",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<PolygonPoint> polygonPoints;

    @OneToMany(mappedBy = "flight",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<FlightPoint> flightPoints;
}
