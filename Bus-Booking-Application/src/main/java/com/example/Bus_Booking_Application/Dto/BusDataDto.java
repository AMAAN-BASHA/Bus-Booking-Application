package com.example.Bus_Booking_Application.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDataDto {
    private Long id;
    private String busNumber;
    private String busName;
    private Long capacity;
    private String source;
    @NotEmpty(message = "destination should not be empty")
    private String destination;
    @NotEmpty(message = "price should not be empty")
    private Long price;
    @NotEmpty(message = "date should not be empty")
    private String  date;
    @NotEmpty(message = "time should not be empty")
    private String time;
    @NotEmpty(message = "duration should not be empty")
    private Long duration;
    @NotEmpty(message = "availableSeats should not be empty")
    private Long availableSeats;
}

