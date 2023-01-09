package com.example.sherlock_chan_car_rental_service.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateScheduleDto {
   @NotNull
    private LocalDate starting_date;
   @NotNull
    private LocalDate ending_date;

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
}
