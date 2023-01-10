package com.example.sherlock_chan_car_rental_service.service;

import com.example.sherlock_chan_car_rental_service.dto.ScheduleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    Page<ScheduleDto> findAll(Pageable pageable);
    Page<ScheduleDto> findByCity(String city_name, Pageable pageable);
    Page<ScheduleDto> findByCompany(Pageable pageable);

}
