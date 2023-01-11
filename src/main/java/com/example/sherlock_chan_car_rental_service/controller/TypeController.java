package com.example.sherlock_chan_car_rental_service.controller;

import com.example.sherlock_chan_car_rental_service.dto.TypeCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.TypeDto;
import com.example.sherlock_chan_car_rental_service.dto.TypeUpdateDto;
import com.example.sherlock_chan_car_rental_service.service.TypeService;
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

@RestController
@RequestMapping("/type")

public class TypeController {
    private TypeService typeService;

    public TypeController(TypeService typeService){
        this.typeService=typeService;
    }

    @ApiOperation(value = "Get all types")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping
    public ResponseEntity<Page<TypeDto>> findAllTypes(@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(typeService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("listById/{id}")
    public ResponseEntity<TypeDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(typeService.findById(id), HttpStatus.OK);
    }

    @GetMapping("listByName/{id}")
    public ResponseEntity<String> findTypeNameById(@PathVariable("id") Long id){
        return new ResponseEntity<>(typeService.findTypeNameById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TypeDto> createType(@RequestBody @Valid TypeCreateDto typeCreateDto) {
        return new ResponseEntity<>(typeService.createType(typeCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("put/{id}")
    public ResponseEntity<TypeDto> updateType(@PathVariable("id") Long id,
                                              @RequestBody @Valid TypeUpdateDto typeUpdateDto) {
        return new ResponseEntity<>(typeService.updateType(id, typeUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    void delete(@PathVariable("id") Long id) {
        typeService.deleteById(id);
        System.out.printf("ok delete");
    }

}
