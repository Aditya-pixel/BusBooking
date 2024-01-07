package com.springdatajpa.BusBooking.repository;


import com.springdatajpa.BusBooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUserId(int userId);
    boolean existsByUsernameAndPassword(String username, String password);

}
