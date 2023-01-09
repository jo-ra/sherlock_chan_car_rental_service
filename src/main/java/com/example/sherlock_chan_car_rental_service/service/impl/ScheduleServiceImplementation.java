package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.dto.ScheduleDto;
import com.example.sherlock_chan_car_rental_service.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service

public class ScheduleServiceImplementation implements ScheduleService {

    @Override
    public Page<ScheduleDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ScheduleDto> findByCity(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ScheduleDto> findByCompany(Pageable pageable) {
        return null;
    }
}
