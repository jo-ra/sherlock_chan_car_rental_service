package com.example.sherlock_chan_car_rental_service.controller;

import com.example.sherlock_chan_car_rental_service.dto.ReservationDto;
import com.example.sherlock_chan_car_rental_service.service.ReservationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/schedule")

public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @ApiOperation(value = "Get all schedules")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping
    public ResponseEntity<Page<ReservationDto>> getAllSchedules(@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(reservationService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/listByCity/{city}")
    public ResponseEntity<List<ReservationDto>> findByCity(@PathVariable("city") String city){
        return new ResponseEntity<>(reservationService.findByCity(city),HttpStatus.OK);
    }

    @GetMapping("/listByCompany/{company}")
    public ResponseEntity<List<ReservationDto>> findByCompany(@PathVariable("company") String company){
        return new ResponseEntity<>(reservationService.findByCompany(company),HttpStatus.OK);
    }

    @GetMapping("/listByDate/{start_date}/{end_date}")
    public ResponseEntity<List<ReservationDto>> findByDate(@PathVariable("start_date")String start_date,
                                                           @PathVariable("end_date") String end_date){
        System.out.printf("%s %s",start_date,end_date);
        return new ResponseEntity<>(reservationService.findByDate(LocalDate.parse(start_date),LocalDate.parse(end_date)),HttpStatus.OK);
    }
}
