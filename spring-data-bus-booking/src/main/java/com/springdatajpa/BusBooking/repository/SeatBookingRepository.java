package com.springdatajpa.BusBooking.repository;

import com.springdatajpa.BusBooking.entity.SeatBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SeatBookingRepository extends JpaRepository<SeatBooking, Long> {

    // Custom query to find the highest booked seat number for a given bus
    @Query("SELECT MAX(sb.seatNumber) FROM SeatBooking sb WHERE sb.bus.id = :busId")
    Integer findHighestBookedSeat(@Param("busId") int busId);

    // Method to check if a seat with a specific seat number is booked for a given bus
    boolean existsByBusBusIdAndSeatNumber(int busId, int seatNumber);

    Optional<SeatBooking> findByUserUserIdAndBusBusIdAndSeatNumber(int userId, int busId, int seatNumber);

    Optional<SeatBooking> findByBookingId(int bookingId);

    // Additional methods for seat booking-related queries can be added as needed
}

