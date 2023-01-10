package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.dto.ReservationDto;
import com.example.sherlock_chan_car_rental_service.mapper.ReservationMapper;
import com.example.sherlock_chan_car_rental_service.repository.CompanyRepository;
import com.example.sherlock_chan_car_rental_service.repository.ReservationRepository;
import com.example.sherlock_chan_car_rental_service.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ReservationServiceImplementation implements ReservationService {

    private ReservationMapper reservationMapper;
    private ReservationRepository reservationRepository;
    private CompanyRepository companyRepository;

    public ReservationServiceImplementation(ReservationMapper reservationMapper, CompanyRepository companyRepository, ReservationRepository reservationRepository){
        this.reservationMapper = reservationMapper;
        this.companyRepository=companyRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return (Page<ReservationDto>) reservationRepository
                .findAll(pageable)
                .map(reservationMapper::scheduleToScheduleDto);
    }

    @Override
    public List<ReservationDto> findByCity(String city_name) {
        return  reservationRepository
                .findAll().stream()
                .filter(reservation -> reservation.getCompany().getAddress().getCity().equals(city_name))
                .map(reservationMapper::scheduleToScheduleDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ReservationDto> findByCompany(String company_name) {
       // Optional<Company> company=companyRepository.findCompanyByName(company_name);
        return reservationRepository
                .findAll().stream()
                .filter(reservation -> reservation.getCompany().getName().equals(company_name))
                .map(reservationMapper::scheduleToScheduleDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<ReservationDto> findByDate(LocalDate start_date, LocalDate end_date) {
        return reservationRepository.findAll().stream()
                .filter(reservation -> ( (start_date.isAfter(reservation.getStarting_date()) ||
                        start_date.isEqual(reservation.getStarting_date()))
                && (end_date.isEqual(reservation.getEnding_date()) || (end_date).isBefore(reservation.getEnding_date()))))
                .map(reservationMapper::scheduleToScheduleDto)
                .collect(Collectors.toList());

    }
}
