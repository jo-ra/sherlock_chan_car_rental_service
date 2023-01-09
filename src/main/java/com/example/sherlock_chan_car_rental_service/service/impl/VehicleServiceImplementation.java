package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.dto.VehicleCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.VehicleDto;
import com.example.sherlock_chan_car_rental_service.dto.VehicleUpdateDto;
import com.example.sherlock_chan_car_rental_service.service.VehicleService;
import org.springframework.stereotype.Service;

@Service

public class VehicleServiceImplementation implements VehicleService {

    @Override
    public VehicleDto createVehicle(VehicleCreateDto vehicleCreateDto) {
        return null;
    }

    @Override
    public VehicleDto updateVehicle(Long id, VehicleUpdateDto vehicleUpdateDto) {
        return null;
    }
}
