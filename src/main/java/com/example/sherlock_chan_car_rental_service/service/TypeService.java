package com.example.sherlock_chan_car_rental_service.service;

import com.example.sherlock_chan_car_rental_service.dto.TypeCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.TypeDto;
import com.example.sherlock_chan_car_rental_service.dto.TypeUpdateDto;

public interface TypeService {

    TypeDto createType(TypeCreateDto typeCreateDto);
    TypeDto updateType(Long id, TypeUpdateDto typeUpdateDto);
}
