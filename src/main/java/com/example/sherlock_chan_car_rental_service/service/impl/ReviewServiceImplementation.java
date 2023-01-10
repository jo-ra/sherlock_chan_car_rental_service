package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.domain.Review;
import com.example.sherlock_chan_car_rental_service.dto.ReviewCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.ReviewDto;
import com.example.sherlock_chan_car_rental_service.dto.ReviewUpdateDto;
import com.example.sherlock_chan_car_rental_service.exception.NotFoundException;
import com.example.sherlock_chan_car_rental_service.mapper.CompanyMapper;
import com.example.sherlock_chan_car_rental_service.mapper.ReviewMapper;
import com.example.sherlock_chan_car_rental_service.repository.CompanyRepository;
import com.example.sherlock_chan_car_rental_service.repository.ReviewRepository;
import com.example.sherlock_chan_car_rental_service.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImplementation implements ReviewService {
    private ReviewRepository reviewRepository;
    private ReviewMapper reviewMapper;

    public ReviewServiceImplementation(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public Page<ReviewDto> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(reviewMapper::reviewToReviewDto);
    }

    @Override
    public Page<ReviewDto> findByCity(String city, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ReviewDto> findByCompany(Long company_id, Pageable pageable) {
        return reviewRepository.findByCompany(company_id,pageable)
                .map(reviewMapper::reviewToReviewDto);
    }
    @Override
    public ReviewDto createReview(ReviewCreateDto reviewCreateDto) {
        Review review=reviewMapper.reviewCreateDtoToReview(reviewCreateDto);
        reviewRepository.save(review);
        return reviewMapper.reviewToReviewDto(review);
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewUpdateDto reviewUpdateDto) {
        Review review=reviewRepository.findById(id)
                .orElseThrow(()->new NotFoundException(String.format("Review with id: %d not found")));
        review.setStar(reviewUpdateDto.getStar());
        review.setComment(reviewUpdateDto.getComment());
        return reviewMapper.reviewToReviewDto(reviewRepository.save(review));


    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);

    }
}
