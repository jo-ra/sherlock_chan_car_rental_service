package com.example.sherlock_chan_car_rental_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyUpdateDto {
    @NotEmpty(message = "Text can't be empty")
    private String name;
    @NotEmpty(message = "Text can't be empty")
    private String description;
    @NotNull
    @JsonProperty("numVehicles")
    private Integer numVehicles;

    //address?

}
