package com.example.Bus_Booking_Application.ControllerRestApi;

import com.example.Bus_Booking_Application.Dto.BusDataDto;
import com.example.Bus_Booking_Application.Dto.UserDto;
import com.example.Bus_Booking_Application.Services.BusDataService;
import com.example.Bus_Booking_Application.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = " HOME PAGE API  ",
        description = "Endpoints for searching buses "
)
@RestController
@RequestMapping("/api/home")
@AllArgsConstructor
public class HomeControllerApi {

    private final UserService userService;
    private final BusDataService busDataService;

    @Operation(summary = "Current logged in user details", description = "Fetches the details of the user logged in ")
    @GetMapping("/user")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Search bus routes", description = "Fetches all bus routes based on the source , destination , date")
    @GetMapping("/buses")
    public ResponseEntity<?> searchBuses(@RequestParam String from, @RequestParam String to, @RequestParam String date) {
        List<BusDataDto> buses = busDataService.searchBuses(from, to, date);
        if (buses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No buses found for the selected route and date.");
        }
        return ResponseEntity.ok(buses);
    }
}