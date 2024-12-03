package com.example.Bus_Booking_Application.Services;


import com.example.Bus_Booking_Application.Entity.Bookings;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface BookingService {
    List<Bookings> viewAllBus();
    List<Bookings> getBookingsByEmail(String email) ;
    Bookings saveBooking(Bookings booking);
    void generatePdfWithPDFBox(Bookings booking, HttpServletResponse response) throws IOException;
}

