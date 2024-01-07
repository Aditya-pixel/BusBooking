package com.springdatajpa.BusBooking.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class BusRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeId;

    @ManyToOne
    @JoinColumn(name = "busId")
    private Bus bus;

    private String source;
    private String destination;
    private float distance;
    private int eta;
}
