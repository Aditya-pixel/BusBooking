package com.springdatajpa.BusBooking.entity;

import com.springdatajpa.BusBooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int registerUser(String username, String password) {
        // For simplicity, storing passwords in plain text (not recommended in a real application)
        if (userRepository.existsByUsernameAndPassword(username, password)) {
            throw new RuntimeException("User with the same username and password already exists");
        }
        User user = new User(username, password);
        User savedUser = userRepository.save(user);
        return savedUser.getUserId();
    }

    public Optional<Integer> authenticateUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username, password);
        return userOptional.map(User::getUserId);
    }


}