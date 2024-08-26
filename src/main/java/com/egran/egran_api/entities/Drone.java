package com.egran.egran_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drone")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Drone {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "model")
    private String model;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

}
