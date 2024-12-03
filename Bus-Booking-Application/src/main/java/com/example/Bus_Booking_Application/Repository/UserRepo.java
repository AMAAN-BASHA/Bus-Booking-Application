package com.example.Bus_Booking_Application.Repository;

import com.example.Bus_Booking_Application.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(Long number);
}
