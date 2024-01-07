package com.springdatajpa.BusBooking.repository;

import com.springdatajpa.BusBooking.entity.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {
    List<BusRoute> findBySourceAndDestination(String source, String destination);
}
