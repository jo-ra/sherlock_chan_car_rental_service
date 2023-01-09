package com.example.sherlock_chan_car_rental_service.mapper;

import com.example.sherlock_chan_car_rental_service.domain.Schedule;
import com.example.sherlock_chan_car_rental_service.dto.ScheduleCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.ScheduleDto;
import com.example.sherlock_chan_car_rental_service.exception.NotFoundException;
import com.example.sherlock_chan_car_rental_service.repository.CompanyRepository;
import com.example.sherlock_chan_car_rental_service.repository.VehicleRepository;

public class ScheduleMapper {

    private CompanyMapper companyMapper;
    private VehicleMapper vehicleMapper;
    private CompanyRepository companyRepository;
    private VehicleRepository vehicleRepository;

    public ScheduleMapper(CompanyRepository companyRepository, VehicleRepository vehicleRepository
            , CompanyMapper companyMapper, VehicleMapper vehicleMapper){
        this.companyMapper = companyMapper;
        this.vehicleMapper = vehicleMapper;
        this.companyRepository = companyRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public ScheduleDto scheduleToScheduleDto(Schedule schedule){
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setVehicleDto(vehicleMapper.vehicleToVehicleDto(schedule.getVehicle()));
        scheduleDto.setCompanyDto(companyMapper.companyToCompanyDto(schedule.getCompany()));

        scheduleDto.setStarting_date(schedule.getStarting_date());
        scheduleDto.setEnding_date(schedule.getEnding_date());

        return scheduleDto;
    }

    public Schedule scheduleCreateDtoToSchedule(ScheduleCreateDto scheduleCreateDto){
        Schedule schedule = new Schedule();

        schedule.setEnding_date(scheduleCreateDto.getEnding_date());
        schedule.setStarting_date(scheduleCreateDto.getStarting_date());

        schedule.setCompany(companyRepository
                .findById(scheduleCreateDto.getCompany_id())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Company with provided id %d has not been found", scheduleCreateDto.getCompany_id())
                )));

        schedule.setVehicle(vehicleRepository
                .findById(scheduleCreateDto.getVehicle_id())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Vehicle with provided id %d has not been found", scheduleCreateDto.getVehicle_id())
                )));

        return schedule;
    }
}
