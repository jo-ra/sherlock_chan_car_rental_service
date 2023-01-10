package com.example.sherlock_chan_car_rental_service.service;

import com.example.sherlock_chan_car_rental_service.dto.TypeCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.TypeDto;
import com.example.sherlock_chan_car_rental_service.dto.TypeUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeService {

    Page<TypeDto> findAll(Pageable pageable);

    String findTypeNameById(Long id);
    TypeDto createType(TypeCreateDto typeCreateDto);
    TypeDto updateType(Long id, TypeUpdateDto typeUpdateDto);
}
