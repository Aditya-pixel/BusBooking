package com.springdatajpa.BusBooking.entity;

import com.springdatajpa.BusBooking.repository.BusRepository;
import com.springdatajpa.BusBooking.repository.SeatBookingRepository;
import com.springdatajpa.BusBooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatBookingService {

    private final SeatBookingRepository seatBookingRepository;
    private final BusRepository busRepository;
    private final UserRepository userRepository;

    @Autowired
    public SeatBookingService(SeatBookingRepository seatBookingRepository, BusRepository busRepository, UserRepository userRepository) {
        this.seatBookingRepository = seatBookingRepository;
        this.busRepository = busRepository;
        this.userRepository = userRepository;
    }

    public int bookNextAvailableSeat(int busId, int userId) {
        // Fetch the Bus object from the database
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + busId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Get the current occupancy and total seats
        int currentOccupancy = bus.getCurrentOccupancy();
        int totalSeats = bus.getTotalSeats();

        // Check if there are available seats
        if (currentOccupancy < totalSeats) {
            // Find the next available seat number
            int nextSeatNumber = findNextAvailableSeat(busId, totalSeats);

            // Book the seat
            SeatBooking newBooking = new SeatBooking();
            newBooking.setBus(bus);
            newBooking.setSeatNumber(nextSeatNumber);
            newBooking.setUser(user);
            seatBookingRepository.save(newBooking);

            // Update the current occupancy in the Bus object
            bus.setCurrentOccupancy(currentOccupancy + 1);
            busRepository.save(bus);

            return nextSeatNumber;
        } else {
            throw new RuntimeException("No available seats on Bus with ID: " + busId);
        }
    }

    private int findNextAvailableSeat(int busId, int totalSeats) {
        // Find the highest seat number booked for the given busId
        Integer highestBookedSeat = seatBookingRepository.findHighestBookedSeat(busId);

        // If no seat is booked yet, start with seat number 1; otherwise, increment the seat number
        int nextSeatNumber = (highestBookedSeat != null) ? highestBookedSeat + 1 : 1;

        // Circulate back to 1 if the next seat number exceeds the total seats
        if (nextSeatNumber > totalSeats) {
            nextSeatNumber = 1;
        }

        // Use a loop to find the next available seat
        for (int i = 0; i < totalSeats; i++) {
            if (!seatBookingRepository.existsByBusBusIdAndSeatNumber(busId, nextSeatNumber)) {
                break; // Found an available seat, exit the loop
            }

            // Increment the seat number and circulate back to 1 if needed
            nextSeatNumber = (nextSeatNumber % totalSeats) + 1;
        }

        return nextSeatNumber;
    }

    public int getBookingIdByUserIdBusIdAndSeatNumber(int userId, int busId, int seatNumber) {
        // Find the SeatBooking by userId, busId, and seatNumber
        SeatBooking seatBooking = seatBookingRepository.findByUserUserIdAndBusBusIdAndSeatNumber(userId, busId, seatNumber)
                .orElseThrow(() -> new RuntimeException("Seat booking not found for User ID: " + userId + ", Bus ID: " + busId + ", Seat Number: " + seatNumber));

        // Return the routeId from the SeatBooking entity
        return seatBooking.getBookingId();
    }

    public void cancelSeatBookingByBookingId(int bookingId) {
        // Find the seat booking by bookingId
        SeatBooking seatBooking = seatBookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Seat booking not found for Booking ID: " + bookingId));

        // Get the associated Bus entity
        Bus bus = seatBooking.getBus();

        // Increase the currentOccupancy by 1
        bus.setCurrentOccupancy(bus.getCurrentOccupancy() - 1);

        // Save the updated Bus entity
        busRepository.save(bus);

        // Delete the seat booking
        seatBookingRepository.delete(seatBooking);
    }
}
