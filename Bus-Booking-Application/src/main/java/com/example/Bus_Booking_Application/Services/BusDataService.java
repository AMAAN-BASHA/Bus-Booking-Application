package com.example.Bus_Booking_Application.Services;



import com.example.Bus_Booking_Application.Dto.BusDataDto;

import java.util.List;

public interface BusDataService {

    BusDataDto addBus(BusDataDto busDataDto) ;
    BusDataDto updateBus(BusDataDto busDataDto, Long id);
    void deleteBus(Long id) ;
    BusDataDto viewBus(Long id) ;
    List<BusDataDto> viewAllBus();
    List<BusDataDto> searchBuses(String source, String destination, String date);


}