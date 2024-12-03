package com.example.Bus_Booking_Application.Services;

import com.example.Bus_Booking_Application.Dto.UserDto;
import com.example.Bus_Booking_Application.Entity.User;
import com.example.Bus_Booking_Application.Repository.UserRepo;
import com.example.Bus_Booking_Application.exception.Emailpresent;
import com.example.Bus_Booking_Application.exception.PhoneNumberPresent;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private UserRepo userRepo;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(UserDto userDto)   {
        User user = modelMapper.map(userDto,User.class);
        Optional<User> optionalUser = userRepo.findByEmail(userDto.getEmail());
        if(optionalUser.isPresent()){
            throw new Emailpresent("Email Already Present");
        }
        Optional<User> optionalUser1 = userRepo.findByPhoneNumber(userDto.getPhoneNumber());
        if(optionalUser1.isPresent()){
            throw new PhoneNumberPresent("PhoneNumber Already Present");
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User user = userRepo.findById(id).get();
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setName(userDto.getName());
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDto viewUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> viewAllUsers() {

        return userRepo.findAll().stream()
                .map(user ->
                        modelMapper.map(user, UserDto.class)
                )
                .toList();
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return modelMapper.map(userRepo.findByEmail(email), UserDto.class);
    }

}

