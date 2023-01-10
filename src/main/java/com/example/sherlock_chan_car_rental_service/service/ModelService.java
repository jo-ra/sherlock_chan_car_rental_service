package com.example.sherlock_chan_car_rental_service.service;

import com.example.sherlock_chan_car_rental_service.dto.ModelCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.ModelDto;
import com.example.sherlock_chan_car_rental_service.dto.ModelUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModelService {

    Page<ModelDto> findAll(Pageable pageable);

    ModelDto createModel(ModelCreateDto modelCreateDto);

    ModelDto updateModel(Long id, ModelUpdateDto modelUpdateDto);

    void deleteById(Long id);

}
