package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.dto.ReviewDto;
import com.example.sherlock_chan_car_rental_service.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImplementation implements ReviewService {
    @Override
    public Page<ReviewDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ReviewDto> findByCity(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ReviewDto> findByCompany(Pageable pageable) {
        return null;
    }
}
