package com.springdatajpa.BusBooking.entity;

import com.springdatajpa.BusBooking.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    BusRepository busRepository;
    @Autowired
    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public void addBus(Bus bus) {
        busRepository.save(bus);
    }

    public void updateBus(int busId, Bus updatedBus) {
        Optional<Bus> existingBusOptional = busRepository.findById(busId);
        if (existingBusOptional.isPresent()) {
            Bus existingBus = existingBusOptional.get();
            // Update the properties with the new values
            existingBus.setCurrentOccupancy(updatedBus.getCurrentOccupancy());
            existingBus.setTotalSeats(updatedBus.getTotalSeats());
            existingBus.setAvailableDays(updatedBus.getAvailableDays());
            // Save the updated bus
            busRepository.save(existingBus);
        } else {
            throw new RuntimeException("Bus not found with ID: " + busId);
        }
    }

    public void deleteBus(int busId) {

        busRepository.deleteById(busId);
    }

    public Optional<Bus> getBusById(int busId) {
        return busRepository.findById(busId);
    }

    public boolean doesBusExist(int busId) {
        Optional<Bus> existingBus = busRepository.findById(busId);
        return existingBus.isPresent();
    }

    public int getAvailableSeats(Bus bus) {
        // Assuming totalSeats and currentOccupancy are properties of the Bus class
        int totalSeats = bus.getTotalSeats();
        int currentOccupancy = bus.getCurrentOccupancy();

        // Calculate the available seats
        int availableSeats = totalSeats - currentOccupancy;

        return Math.max(0, availableSeats); // Ensure non-negative value
    }
}
