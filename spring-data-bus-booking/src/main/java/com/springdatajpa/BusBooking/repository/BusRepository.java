package com.springdatajpa.BusBooking.repository;



import com.springdatajpa.BusBooking.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Integer> {
}
