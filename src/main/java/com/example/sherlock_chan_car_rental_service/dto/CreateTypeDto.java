package com.example.sherlock_chan_car_rental_service.dto;

import javax.validation.constraints.NotEmpty;

public class CreateTypeDto {
    @NotEmpty(message = "Text can't be empty")
    private String name;
}
