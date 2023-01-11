package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.domain.Reservation;
import com.example.sherlock_chan_car_rental_service.dto.ReservationDto;
import com.example.sherlock_chan_car_rental_service.mapper.ReservationMapper;
import com.example.sherlock_chan_car_rental_service.repository.CompanyRepository;
import com.example.sherlock_chan_car_rental_service.repository.ReservationRepository;
import com.example.sherlock_chan_car_rental_service.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
                .map(reservationMapper::reservationToReservationDto);
    }

    @Override
    public List<ReservationDto> findByCity(String city_name, boolean sort_by_price) {
        return  reservationRepository
                .findAll().stream()
                .filter(reservation -> reservation.getCompany().getAddress().getCity().equalsIgnoreCase(city_name))
                .map(reservationMapper::reservationToReservationDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ReservationDto> findByCompany(String company_name, boolean sort_by_price) {
       // Optional<Company> company=companyRepository.findCompanyByName(company_name);
        return reservationRepository
                .findAll().stream()
                .filter(reservation -> reservation.getCompany().getName().equalsIgnoreCase(company_name))
                .map(reservationMapper::reservationToReservationDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<ReservationDto> findByDate(LocalDate start_date, LocalDate end_date, boolean sort_by_price) {

        List<Reservation> fetchedReservations = reservationRepository.findAll();
        List<Reservation> preResult = new ArrayList<>();
        List<ReservationDto> resultList = new ArrayList<>();

        for (Reservation currentReservation : fetchedReservations) {
            if ((start_date.isAfter(currentReservation.getStarting_date()) || start_date.isEqual(currentReservation.getStarting_date())
                    && (end_date.isEqual(currentReservation.getEnding_date()) || end_date.isBefore(currentReservation.getEnding_date())))
                    && currentReservation.getIs_active() == 0) {
                if (!preResult.contains(currentReservation)) {
                    preResult.add(currentReservation);
                }
            }
        }

        preResult.sort(new Comparator<Reservation>() {
            @Override
            public int compare(Reservation o1, Reservation o2) {
                if (sort_by_price) {
                    return o1.getVehicle().getModel().getPrice().compareTo(o2.getVehicle().getModel().getPrice());
                } else {
                    return (-1) * o1.getVehicle().getModel().getPrice().compareTo(o2.getVehicle().getModel().getPrice());
                }
            }
        });

        for (Reservation reservation : preResult) {
            resultList.add(reservationMapper.reservationToReservationDto(reservation));
        }

        return resultList;
    }

    @Override
    public List<ReservationDto> filterByAll(String vehicle_type, String city_name, String company_name, LocalDate start_date, LocalDate end_date, boolean sort_by_price) {
        List<Reservation> fetchedReservations = reservationRepository.findAll();
        List<Reservation> preResult = new ArrayList<>();
        List<ReservationDto> resultList = new ArrayList<>();

        for(int i = 0;i<fetchedReservations.size();i++){
            Reservation currentReservation = fetchedReservations.get(i);
            // filter by company

            if(currentReservation.getVehicle().getType().getName().equalsIgnoreCase(vehicle_type)){
                if(currentReservation.getCompany().getName().equalsIgnoreCase(company_name)){
                    if(currentReservation.getCompany().getAddress().getCity().equalsIgnoreCase(city_name)){
                        if ((start_date.isAfter(currentReservation.getStarting_date()) || start_date.isEqual(currentReservation.getStarting_date())
                                && (end_date.isEqual(currentReservation.getEnding_date()) || end_date.isBefore(currentReservation.getEnding_date())))
                                && currentReservation.getIs_active() == 0) {
                            if(!preResult.contains(currentReservation)){
                                preResult.add(currentReservation);
                            }
                        }
                    }
                }
            }
        }

        preResult.sort(new Comparator<Reservation>() {
            @Override
            public int compare(Reservation o1, Reservation o2) {
                if(sort_by_price){
                    return o1.getVehicle().getModel().getPrice().compareTo(o2.getVehicle().getModel().getPrice());
                }else{
                    return (-1) * o1.getVehicle().getModel().getPrice().compareTo(o2.getVehicle().getModel().getPrice());
                }
            }
        });

        for(Reservation reservation : preResult){
            resultList.add(reservationMapper.reservationToReservationDto(reservation));
        }

        return resultList;
    }
}
