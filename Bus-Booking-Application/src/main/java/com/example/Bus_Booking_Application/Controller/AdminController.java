package com.example.Bus_Booking_Application.Controller;


import com.example.Bus_Booking_Application.Dto.BusDataDto;
import com.example.Bus_Booking_Application.Dto.UserDto;
import com.example.Bus_Booking_Application.Entity.Bookings;
import com.example.Bus_Booking_Application.Repository.BusDataRepo;
import com.example.Bus_Booking_Application.Services.BookingService;
import com.example.Bus_Booking_Application.Services.BusDataService;
import com.example.Bus_Booking_Application.Services.UserService;
import com.example.Bus_Booking_Application.exception.DuplicateBusNumberException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    BusDataService busDataService;
    UserService userService;
    BookingService bookingService;
    BusDataRepo busDataRepo;
    @GetMapping
    public String showAdmin() {
        return "admin";  //
    }

    @GetMapping("/admin-addBus")
    public String showBusRegistrationForm(Model model) {
        BusDataDto busDataDto = new BusDataDto();
        model.addAttribute("busDataDto", busDataDto);
        return "admin-addBus";
    }

    @PostMapping("/admin-addBus/save")
    public String registerBus(@ModelAttribute BusDataDto busDataDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/admin-addBus";
        }
        try {
            busDataService.addBus(busDataDto);
            return "redirect:/admin?success";
        } catch (DuplicateBusNumberException ex) {
            model.addAttribute("errorMessage", "BUS NUMBER ALREADY PRESENT");
            return "admin-addBus";
        }

    }

    @GetMapping("/admin-viewBuses")
    public String viewAllBus(Model model) {
        List<BusDataDto> busDataDto = busDataService.viewAllBus();
        model.addAttribute("busDataDto", busDataDto);
        return "admin-viewBuses";
    }

    @GetMapping("/admin-viewUsers")
    public String users(Model model)  {
        List<UserDto> users = userService.viewAllUsers();
        model.addAttribute("users", users);
        return "admin-viewUsers";
    }

    @GetMapping("/admin-deleteBus")
    public String showDeleteBusPage(Model model) {
        List<BusDataDto> buses = busDataService.viewAllBus();
        model.addAttribute("buses", buses);
        return "admin-deleteBus";
    }

    // Delete user
    @GetMapping("/admin-deleteBus/{id}")
    public String deleteUser(@PathVariable() Long id) {
        busDataService.deleteBus(id);
        return "redirect:/admin?success";
    }

    @GetMapping("/admin-updateBus")
    public String showUpdateBusPage(Model model){
        // Fetch all buses to display on the update page
        List<BusDataDto> buses = busDataService.viewAllBus();

        model.addAttribute("buses", buses);
        return "admin-updateBus";
    }

    @GetMapping("/admin-updateBus/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        BusDataDto user = busDataService.viewBus(id);
        model.addAttribute("user", user);
        return "admin-updateform"; // This page will be for editing a user
    }

    @PostMapping("/admin-updateform/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute BusDataDto busDataDto,BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/admin-updateform";
        }
        try {
            busDataService.updateBus(busDataDto,id);
            return "redirect:/admin?success";
        } catch (DuplicateBusNumberException ex) {
            model.addAttribute("errorMessage", "BUS NUMBER ALREADY PRESENT");
            return "admin-updateform";
        }
    }

    @GetMapping("/admin-viewBookings")
    public String viewAllBookings(Model model) {
        List<Bookings> bookings = bookingService.viewAllBus();
        model.addAttribute("bookings", bookings);
        return "admin-viewBookings";
    }
}
