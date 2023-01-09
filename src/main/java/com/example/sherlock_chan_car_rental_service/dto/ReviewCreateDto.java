package com.example.sherlock_chan_car_rental_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ReviewCreateDto {
    @NotEmpty(message = "Text can't be empty")
    private String comment;
    @Min(value=1)
    @Max(value=5)
    @JsonProperty("star")
    private Integer star;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }
}
