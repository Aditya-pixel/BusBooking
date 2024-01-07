package com.springdatajpa.BusBooking.entity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int busId;

    private String busName;
    private int totalSeats;
    private int currentOccupancy;
    private String availableDays;

    public Bus() {}
    public Bus(String busName, int totalSeats, int currentOccupancy, String availableDays) {
        this.busName = busName;
        this.totalSeats = totalSeats;
        this.currentOccupancy = currentOccupancy;
        this.availableDays = availableDays;
    }
}