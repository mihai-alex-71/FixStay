package com.fixStay.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private Double pricePerNight;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

}
