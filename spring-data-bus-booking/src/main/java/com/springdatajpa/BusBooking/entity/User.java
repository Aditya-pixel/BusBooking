package com.springdatajpa.BusBooking.entity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int userId;

    private String username;
    private String password; // Hashed and salted in a real system

    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}