package com.example.sherlock_chan_car_rental_service.controller;

import com.example.sherlock_chan_car_rental_service.dto.*;
import com.example.sherlock_chan_car_rental_service.service.ReviewService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/review")

public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService=reviewService;
    }

    @ApiOperation(value = "Get all reviews")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping
    public ResponseEntity<Page<ReviewDto>> findAllTypes(@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(reviewService.findAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/listById/{id}")
    public ResponseEntity<ReviewDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(reviewService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/listByCity/{city_name}")
    public ResponseEntity<List<ReviewDto>> findByCity(@PathVariable("city_name") String city_name){
        return new ResponseEntity<>(reviewService.findByCity(city_name),HttpStatus.OK);
    }

    @GetMapping("/listByCompany/{company_name}")
    public ResponseEntity<List<ReviewDto>> findByCompany(@PathVariable("company_name") String company_name){
        return new ResponseEntity<>(reviewService.findByCompany(company_name),HttpStatus.OK);
    }

    @GetMapping("/getCompaniesByReview")
    public ResponseEntity<List<CompanyDto>> getCompaniesByReview(){
        return new ResponseEntity<>(reviewService.getCompaniesByReview(),HttpStatus.OK);
    }

    @PostMapping("/createReview")
    public ResponseEntity<ReviewDto> createReview(@RequestBody @Valid ReviewCreateDto reviewCreateDto){
        return new ResponseEntity<>(reviewService.createReview(reviewCreateDto),HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable("id")Long id, @RequestBody @Valid ReviewUpdateDto reviewUpdateDto){
        return new ResponseEntity<>(reviewService.updateReview(id,reviewUpdateDto),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable("id") Long id){
        reviewService.deleteById(id);
    }


}
