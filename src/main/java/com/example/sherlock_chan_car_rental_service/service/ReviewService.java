package com.example.sherlock_chan_car_rental_service.service;

import com.example.sherlock_chan_car_rental_service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    Page<ReviewDto> findAll(Pageable pageable);
    Page<ReviewDto> findByCity(String city,Pageable pageable);
    Page<ReviewDto> findByCompany(Long company_id,Pageable pageable);

    ReviewDto createReview(ReviewCreateDto reviewCreateDto);

    ReviewDto updateReview(Long id, ReviewUpdateDto reviewUpdateDto);

    void deleteById(Long id);

}
