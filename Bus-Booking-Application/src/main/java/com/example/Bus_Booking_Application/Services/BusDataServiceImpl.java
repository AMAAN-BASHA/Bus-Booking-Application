package com.example.Bus_Booking_Application.Services;

import com.example.Bus_Booking_Application.Dto.BusDataDto;
import com.example.Bus_Booking_Application.Entity.BusData;
import com.example.Bus_Booking_Application.Repository.BusDataRepo;
import com.example.Bus_Booking_Application.exception.DuplicateBusNumberException;
import com.example.Bus_Booking_Application.exception.ResouceNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BusDataServiceImpl implements BusDataService {

    BusDataRepo busDataRepo;
    ModelMapper modelMapper;


    @Override
    public BusDataDto addBus(BusDataDto busDataDto)  {
        BusData busData = modelMapper.map(busDataDto, BusData.class);
        if (busDataRepo.existsByBusNumber(busDataDto.getBusNumber())) {
            throw new DuplicateBusNumberException("Bus number already exists.");
        }
        return modelMapper.map(busDataRepo.save(busData), BusDataDto.class);
    }

    @Override
    public BusDataDto updateBus(BusDataDto busDataDto, Long id) {
        BusData busData = busDataRepo.findById(id).
                orElseThrow(()-> new ResouceNotFoundException("user","id",id));
        if (busDataRepo.existsByBusNumber(busDataDto.getBusNumber())) {
            throw new DuplicateBusNumberException("Bus number already exists.");
        }
        busData.setBusNumber(busDataDto.getBusNumber());
        busData.setBusName(busDataDto.getBusName());
        busData.setCapacity(busDataDto.getCapacity());
        busData.setSource(busDataDto.getSource());
        busData.setDestination(busDataDto.getDestination());
        busData.setPrice(busDataDto.getPrice());
        busData.setDate(busDataDto.getDate());
        busData.setTime(busDataDto.getTime());
        busData.setDuration(busDataDto.getDuration());
        busData.setAvailableSeats(busDataDto.getAvailableSeats());
        return modelMapper.map(busDataRepo.save(busData), BusDataDto.class);

    }

    @Override
    public void deleteBus(Long id){
        busDataRepo.deleteById(id);
    }

    @Override
    public BusDataDto viewBus(Long id) {
        BusData busData = busDataRepo.findById(id).
                orElseThrow(()-> new ResouceNotFoundException("user","id",id));;
        return modelMapper.map(busData, BusDataDto.class);

    }

    @Override
    public List<BusDataDto> viewAllBus(){
        return busDataRepo.findAll().stream()
                .map(busData ->
                        modelMapper.map(busData, BusDataDto.class)
                )
                .toList();
    }

    public List<BusDataDto> searchBuses(String source, String destination, String date) {
        return busDataRepo.findBySourceAndDestinationAndDate(source, destination, date)
                .stream()
                .map(busData ->
                        modelMapper.map(busData, BusDataDto.class)
                )
                .toList();
    }

}