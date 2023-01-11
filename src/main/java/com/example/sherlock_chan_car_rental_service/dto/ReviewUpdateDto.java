package com.example.sherlock_chan_car_rental_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewUpdateDto {
    private Long id;

    private String comment;

    private Integer star;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
