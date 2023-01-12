package com.example.sherlock_chan_car_rental_service.service;

import com.example.sherlock_chan_car_rental_service.dto.ReservationCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.ReservationDto;
import com.example.sherlock_chan_car_rental_service.dto.VehicleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    Page<ReservationDto> findAll(Pageable pageable);
    List<ReservationDto> findByCity(String city_name, boolean sort_by_price);
    List<ReservationDto> findByCompany(String company_name, boolean sort_by_price);
    List<ReservationDto> findByDate(LocalDate start_date, LocalDate end_date, boolean sort_by_price);
    List<ReservationDto> filterByAll(String vehicle_type, String city_name, String company_name, LocalDate start_date, LocalDate end_date, boolean sort_by_price);

    ReservationDto createReservationByType(ReservationCreateDto reservationCreateDto, Long typeId);
    ReservationDto createReservationByModel(ReservationCreateDto reservationCreateDto, Long modelId);
    List<VehicleDto> listAvailableVehicles();
}
