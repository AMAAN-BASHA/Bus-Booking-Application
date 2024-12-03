package com.example.Bus_Booking_Application.Controller;

import com.example.Bus_Booking_Application.Dto.UserDto;
import com.example.Bus_Booking_Application.Entity.Bookings;
import com.example.Bus_Booking_Application.Services.BookingService;
import com.example.Bus_Booking_Application.Services.UserService;
import com.example.Bus_Booking_Application.exception.Emailpresent;
import com.example.Bus_Booking_Application.exception.PhoneNumberPresent;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    BookingService bookingService;
    UserService userService;

    @GetMapping
    public String showUser() {
        return "user";  //
    }

    @GetMapping("/user-viewBookings")
    public String viewAllBookings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Bookings> bookings = bookingService.getBookingsByEmail(email);
        if (bookings.isEmpty()) {
            model.addAttribute("errorMessage", "No Bookings found .");
        }
        model.addAttribute("bookings", bookings);
        return "user-viewBookings";
    }

    @GetMapping("/user-update")
    public String showUpdateDetailsForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto userDto = userService.findUserByEmail(email);
        model.addAttribute("userDto", userDto);
        return "user-update";
    }

    @PostMapping("/update-details/{id}")
    public String updateUserDetails(@PathVariable Long id, BindingResult result, @ModelAttribute UserDto userDto1, Model model) {
        userService.updateUser(userDto1,id);
        if (result.hasErrors()) {
            return "register"; // Return to the form page if validation errors exist
        }
        try {
            userService.addUser(userDto1);
            return "redirect:/register?success";
        } catch (Emailpresent ex) {
            model.addAttribute("errorMessage", "Email Already Present");
            return "register";
        } catch (PhoneNumberPresent ex) {
            model.addAttribute("errorMessage", "Phone Number Already Present");
            return "register";
        }

    }

    @GetMapping("/user-editPass")
    public String showPasswordUpdatePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto userDto = userService.findUserByEmail(email);
        model.addAttribute("userDto",userDto );
        return "user-editPass";
    }

}