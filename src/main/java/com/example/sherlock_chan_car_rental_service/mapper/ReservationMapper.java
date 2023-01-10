package com.example.sherlock_chan_car_rental_service.mapper;

import com.example.sherlock_chan_car_rental_service.domain.Reservation;
import com.example.sherlock_chan_car_rental_service.dto.ReservationCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.ReservationDto;
import com.example.sherlock_chan_car_rental_service.exception.NotFoundException;
import com.example.sherlock_chan_car_rental_service.repository.CompanyRepository;
import com.example.sherlock_chan_car_rental_service.repository.VehicleRepository;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    private CompanyMapper companyMapper;
    private VehicleMapper vehicleMapper;
    private CompanyRepository companyRepository;
    private VehicleRepository vehicleRepository;

    public ReservationMapper(CompanyRepository companyRepository, VehicleRepository vehicleRepository
            , CompanyMapper companyMapper, VehicleMapper vehicleMapper){
        this.companyMapper = companyMapper;
        this.vehicleMapper = vehicleMapper;
        this.companyRepository = companyRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public ReservationDto scheduleToScheduleDto(Reservation reservation){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setVehicleDto(vehicleMapper.vehicleToVehicleDto(reservation.getVehicle()));
        reservationDto.setCompanyDto(companyMapper.companyToCompanyDto(reservation.getCompany()));

        reservationDto.setStarting_date(reservation.getStarting_date());
        reservationDto.setEnding_date(reservation.getEnding_date());

        return reservationDto;
    }

    public Reservation scheduleCreateDtoToSchedule(ReservationCreateDto reservationCreateDto){
        Reservation reservation = new Reservation();

        reservation.setEnding_date(reservationCreateDto.getEnding_date());
        reservation.setStarting_date(reservationCreateDto.getStarting_date());

        reservation.setCompany(companyRepository
                .findById(reservationCreateDto.getCompany_id())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Company with provided id %d has not been found", reservationCreateDto.getCompany_id())
                )));

        reservation.setVehicle(vehicleRepository
                .findById(reservationCreateDto.getVehicle_id())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Vehicle with provided id %d has not been found", reservationCreateDto.getVehicle_id())
                )));

        return reservation;
    }
}
