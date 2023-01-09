package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.domain.Type;
import com.example.sherlock_chan_car_rental_service.dto.TypeCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.TypeDto;
import com.example.sherlock_chan_car_rental_service.dto.TypeUpdateDto;
import com.example.sherlock_chan_car_rental_service.mapper.TypeMapper;
import com.example.sherlock_chan_car_rental_service.repository.TypeRepository;
import com.example.sherlock_chan_car_rental_service.service.TypeService;
import org.springframework.stereotype.Service;

@Service

public class TypeServiceImplementation implements TypeService {

    private TypeRepository typeRepository;
    private TypeMapper typeMapper;

    public TypeServiceImplementation(TypeMapper typeMapper, TypeRepository typeRepository){
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    @Override
    public TypeDto createType(TypeCreateDto typeCreateDto) {
//        Type type = typeMapper.typeCreateDtoToType(typeCreateDto);
        return null;
    }

    @Override
    public TypeDto updateType(Long id, TypeUpdateDto typeUpdateDto) {
        return null;
    }
}
