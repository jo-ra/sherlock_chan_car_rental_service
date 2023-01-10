package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.dto.ScheduleDto;
import com.example.sherlock_chan_car_rental_service.mapper.ScheduleMapper;
import com.example.sherlock_chan_car_rental_service.repository.ScheduleRepository;
import com.example.sherlock_chan_car_rental_service.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service

public class ScheduleServiceImplementation implements ScheduleService {

    private ScheduleMapper scheduleMapper;
    private ScheduleRepository scheduleRepository;

    public ScheduleServiceImplementation(ScheduleService scheduleService, ScheduleMapper scheduleMapper){
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public Page<ScheduleDto> findAll(Pageable pageable) {
        return (Page<ScheduleDto>) scheduleRepository
                .findAll(pageable)
                .map(scheduleMapper::scheduleToScheduleDto);
    }

    @Override
    public Page<ScheduleDto> findByCity(String city_name, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ScheduleDto> findByCompany(Pageable pageable) {
        return null;
    }
}
