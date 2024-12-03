package com.example.Bus_Booking_Application.ControllerRestApi;

import com.example.Bus_Booking_Application.Dto.BookDto;
import com.example.Bus_Booking_Application.Dto.BusDataDto;
import com.example.Bus_Booking_Application.Entity.Bookings;
import com.example.Bus_Booking_Application.Services.BookingServiceImpl;
import com.example.Bus_Booking_Application.Services.BusDataService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(
        name = "BOOKINGS MANAGEMENT API ",
        description = "Endpoints for managing bookings "
)
@RestController
@RequestMapping("/api/bookings")
@AllArgsConstructor
public class BookingControllerApi {

    private final BookingServiceImpl bookingsService;
    private final BusDataService busDataService;

    @Operation(summary = "Get the bus details", description = "Fetches the bus detauls based on the bus id from the database")
    @GetMapping("/bus/{busId}")
    public ResponseEntity<BusDataDto> getBusDetails(@PathVariable Long busId) {
        return ResponseEntity.ok(busDataService.viewBus(busId));
    }

    @Operation(summary = "confirms the bus booking", description = "bookings is confirmed and a pdf is generated")
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmBooking(@RequestParam Long busId, @RequestBody BookDto bookDto, HttpServletResponse response) throws IOException {
        BusDataDto bus = busDataService.viewBus(busId);

        Bookings bookings = new Bookings();
        bookings.setBusName(bus.getBusName());
        bookings.setBusNumber(bus.getBusNumber());
        bookings.setDate(bus.getDate());
        bookings.setDestination(bus.getDestination());
        bookings.setTime(bus.getTime());
        bookings.setDuration(bus.getDuration());
        bookings.setPrice(bus.getPrice());
        bookings.setSource(bus.getSource());
        bookings.setName(bookDto.getName());
        bookings.setEmail(bookDto.getEmail());
        bookings.setPhoneNumber(bookDto.getPhoneNumber());
        bookings.setAge(bookDto.getAge());
        bookings.setGender(bookDto.getGender());

        bookingsService.saveBooking(bookings);
        bookingsService.generatePdfWithPDFBox(bookings, response);

        return ResponseEntity.ok("Booking confirmed and PDF generated");
    }
}