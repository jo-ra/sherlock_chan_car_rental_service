package com.example.sherlock_chan_car_rental_service.service;

import com.example.sherlock_chan_car_rental_service.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    Page<ReviewDto> findAll(Pageable pageable);
    Page<ReviewDto> findByCity(Pageable pageable);
    Page<ReviewDto> findByCompany(Pageable pageable);

}
