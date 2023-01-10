package com.example.sherlock_chan_car_rental_service.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReservationCreateDto {
    @NotNull
    private LocalDate starting_date;
    @NotNull
    private LocalDate ending_date;

    @NotNull
    private Long company_id;

    @NotNull
    private Long vehicle_id;

    public LocalDate getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(LocalDate starting_date) {
        this.starting_date = starting_date;
    }

    public LocalDate getEnding_date() {
        return ending_date;
    }

    public void setEnding_date(LocalDate ending_date) {
        this.ending_date = ending_date;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public Long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
}
