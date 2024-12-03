package com.example.Bus_Booking_Application.Repository;


import com.example.Bus_Booking_Application.Entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepo extends JpaRepository<Bookings,Long> {
    List<Bookings> findByEmail(String email);
}
