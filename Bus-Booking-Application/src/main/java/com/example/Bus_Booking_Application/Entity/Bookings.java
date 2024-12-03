package com.example.Bus_Booking_Application.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private int age;
    private String gender;
    private String busNumber;
    private String busName;
    private String source;
    private String destination;
    private double price;
    private String date;
    private String time;
    private Long duration;
}
