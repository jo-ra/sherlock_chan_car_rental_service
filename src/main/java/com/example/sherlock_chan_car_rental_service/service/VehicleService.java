package com.example.sherlock_chan_car_rental_service.service;

import com.example.sherlock_chan_car_rental_service.domain.Vehicle;
import com.example.sherlock_chan_car_rental_service.dto.VehicleCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.VehicleDto;
import com.example.sherlock_chan_car_rental_service.dto.VehicleUpdateDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleService {
    VehicleDto createVehicle(VehicleCreateDto vehicleCreateDto);
    VehicleDto updateVehicle(Long id, VehicleUpdateDto vehicleUpdateDto);
}
