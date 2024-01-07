package com.springdatajpa.BusBooking.entity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class SeatBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "busId")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "routeId")
    private BusRoute route;

    private int seatNumber;

    @Column(name = "bookingTimestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp bookingTimestamp;
}