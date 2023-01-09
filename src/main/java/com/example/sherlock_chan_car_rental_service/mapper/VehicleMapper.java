package com.example.sherlock_chan_car_rental_service.mapper;

import com.example.sherlock_chan_car_rental_service.domain.Vehicle;
import com.example.sherlock_chan_car_rental_service.dto.VehicleCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.VehicleDto;
import com.example.sherlock_chan_car_rental_service.exception.NotFoundException;
import com.example.sherlock_chan_car_rental_service.repository.ModelRepository;
import com.example.sherlock_chan_car_rental_service.repository.TypeRepository;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    private ModelRepository modelRepository;
    private TypeRepository typeRepository;
    private ModelMapper modelMapper;
    private TypeMapper typeMapper;

    public VehicleMapper(ModelRepository modelRepository, TypeRepository typeRepository,
                         ModelMapper modelMapper, TypeMapper typeMapper){
        this.modelMapper = modelMapper;
        this.typeMapper = typeMapper;
        this.modelRepository = modelRepository;
        this.typeRepository = typeRepository;
    }

    public VehicleDto vehicleToVehicleDto(Vehicle vehicle){
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setModelDto(modelMapper.modelToModelDto(vehicle.getModel()));
        vehicleDto.setTypeDto(typeMapper.typeToTypeDto(vehicle.getType()));

        return vehicleDto;
    }

    public Vehicle vehicleCreateDtoToVehicle(VehicleCreateDto vehicleCreateDto){
        Vehicle vehicle = new Vehicle();
        vehicle.setModel(modelRepository
                .findById(vehicleCreateDto.getModel_id())
                .orElseThrow(() -> new NotFoundException(String.format("Model with provided id %d has not been found", vehicleCreateDto.getModel_id()))));
        vehicle.setType(typeRepository.
                findById(vehicleCreateDto.getType_id())
                .orElseThrow(() -> new NotFoundException(String.format("Type with provided id %d has not been found", vehicleCreateDto.getType_id()))));

        return vehicle;
    }
}
