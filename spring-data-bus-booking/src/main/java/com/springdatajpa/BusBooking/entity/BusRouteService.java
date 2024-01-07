package com.springdatajpa.BusBooking.entity;

import com.springdatajpa.BusBooking.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BusRouteService {

    private final BusRouteRepository busRouteRepository;

    @Autowired
    public BusRouteService(BusRouteRepository busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }

    public List<BusRoute> getRoutesBySourceAndDestination(String source, String destination) {
        return busRouteRepository.findBySourceAndDestination(source, destination);
    }
    public void createBusRoute(BusRoute busRoute) {
        // Additional validation and business logic can be added here before saving to the database
        busRouteRepository.save(busRoute);
    }

    // You may need to add more methods for other operations related to BusRoutes
}
