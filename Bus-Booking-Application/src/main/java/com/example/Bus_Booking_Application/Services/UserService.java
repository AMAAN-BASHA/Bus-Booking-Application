package com.example.Bus_Booking_Application.Services;



import com.example.Bus_Booking_Application.Dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto) ;
    UserDto updateUser(UserDto userDto,Long id) ;
    void deleteUser(Long id)  ;
    UserDto viewUser(Long id)  ;
    List<UserDto> viewAllUsers()  ;
    UserDto findUserByEmail(String email)  ;


}