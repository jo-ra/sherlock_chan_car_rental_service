package com.example.sherlock_chan_car_rental_service.mapper;

import com.example.sherlock_chan_car_rental_service.domain.Type;
import com.example.sherlock_chan_car_rental_service.dto.TypeCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.TypeDto;
import org.springframework.stereotype.Component;

@Component
public class TypeMapper {

    public TypeDto typeToTypeDto(Type type){
        TypeDto typeDto=new TypeDto();
        typeDto.setId(type.getId());
        typeDto.setName(type.getName());
        return typeDto;
    }

    public Type typeCreateDtoToType(TypeCreateDto typeCreateDto){
        Type type=new Type();
        type.setName(typeCreateDto.getName());
        return type;
    }
}
