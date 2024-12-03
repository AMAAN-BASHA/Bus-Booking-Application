package com.example.Bus_Booking_Application.ControllerRestApi;

import com.example.Bus_Booking_Application.Dto.UserDto;
import com.example.Bus_Booking_Application.Entity.Bookings;
import com.example.Bus_Booking_Application.Services.BookingService;
import com.example.Bus_Booking_Application.Services.UserService;
import com.example.Bus_Booking_Application.exception.Emailpresent;
import com.example.Bus_Booking_Application.exception.PhoneNumberPresent;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserControllerApi {

    private final BookingService bookingService;
    private final UserService userService;

    @Operation(summary = "Get all bookings", description = "Fetches all the bookings done by the user from the database")
    @GetMapping("/bookings")
    public ResponseEntity<?> viewUserBookings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Bookings> bookings = bookingService.getBookingsByEmail(email);

        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found.");
        }
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Update a user", description = "Updates an existing user's details in the database")
    @PutMapping("/update-details/{id}")
    public ResponseEntity<?> updateUserDetails(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            userService.updateUser(userDto, id);
            return ResponseEntity.ok("User details updated successfully");
        } catch (Emailpresent ex) {
            return ResponseEntity.badRequest().body("Email already present");
        } catch (PhoneNumberPresent ex) {
            return ResponseEntity.badRequest().body("Phone number already present");
        }
    }

    @Operation(summary = "Create a new user", description = "Creating a new valid user in the database")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a user", description = "Deletes a user from the database")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully!!!", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get a users", description = "Fetches the users from the database based on the id")
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.viewUser(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all users", description = "Fetches all users from the database")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.viewAllUsers(), HttpStatus.OK);
    }
}