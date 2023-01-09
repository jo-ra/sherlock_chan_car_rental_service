package com.example.sherlock_chan_car_rental_service.mapper;

import com.example.sherlock_chan_car_rental_service.domain.Review;
import com.example.sherlock_chan_car_rental_service.dto.ReviewDto;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDto reviewToReviewDto(Review review){
        ReviewDto reviewDto=new ReviewDto();
        reviewDto.setComment(review.getComment());
        reviewDto.setStar(review.getStar());
        return reviewDto;
    }

}
