package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.domain.Reservation;
import com.example.sherlock_chan_car_rental_service.domain.Vehicle;
import com.example.sherlock_chan_car_rental_service.dto.ReservationCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.ReservationDto;
import com.example.sherlock_chan_car_rental_service.dto.VehicleDto;
import com.example.sherlock_chan_car_rental_service.mapper.ModelMapper;
import com.example.sherlock_chan_car_rental_service.mapper.ReservationMapper;
import com.example.sherlock_chan_car_rental_service.mapper.VehicleMapper;
import com.example.sherlock_chan_car_rental_service.repository.CompanyRepository;
import com.example.sherlock_chan_car_rental_service.repository.ReservationRepository;
import com.example.sherlock_chan_car_rental_service.repository.VehicleRepository;
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
    private VehicleMapper vehicleMapper;

    private ReservationRepository reservationRepository;
    private CompanyRepository companyRepository;
    private VehicleRepository vehicleRepository;

    public ReservationServiceImplementation(ReservationMapper reservationMapper, CompanyRepository companyRepository,
                                            ReservationRepository reservationRepository, VehicleRepository vehicleRepository,
                                            VehicleMapper vehicleMapper){
        this.reservationMapper = reservationMapper;
        this.companyRepository=companyRepository;
        this.reservationRepository = reservationRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
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

    @Override
    public ReservationDto createReservationByType(ReservationCreateDto reservationCreateDto, Long typeId) {

        /** FUNKCIJA KOJA TREBA DA SE ZAVRSI
         *
         *  Korisnik u reservationCreateDto salje i userid, i vehicle id i company id i datum
         *  Treba da docupam listu dostupnih vozila i da proverim da li se vozilo sa typeId
         *  i sa kompani id iz createDto nalazi u listi dostupnih vozila. Ako se nalazi,
         *  izvrsiti kreiranje rezervacije.
         *
         * */

        List<Vehicle> availableVehicles = listAvailableVehicles()
                .stream()
                .map(vehicleMapper::vehicleDtoToVehicle)
                .collect(Collectors.toList());

        boolean isAvailable = false;

        for(Vehicle vehicle : availableVehicles){
            System.out.println(vehicle.getId() + " " + vehicle.getModel() + " " + vehicle.getCompany().getId());
        }

        for(Vehicle vehicle : availableVehicles){
            if((vehicle.getType().getId().equals(typeId)) && vehicle.getCompany().getId().equals(reservationCreateDto.getCompany_id())){
                isAvailable = true;
                break;
            }
        }

        System.out.println("Available/notAvailable -> " + isAvailable);

        return null;
    }

    public List<VehicleDto> listAvailableVehicles(){
        List<Vehicle> vehiclesResultSet = vehicleRepository.findAll();
        List<Reservation> reservationsResultSet = reservationRepository.findAll();
        List<Vehicle> resultVehicles = new ArrayList<>();
        List<VehicleDto> resultDtos = new ArrayList<>();

        for(Reservation reservation : reservationsResultSet){
            for (Vehicle vehicle : vehiclesResultSet){
                if(!reservation.getVehicle().equals(vehicle)){
                    resultVehicles.add(vehicle);
                }
            }
        }

        for(Vehicle vehicle : resultVehicles){
            resultDtos.add(vehicleMapper.vehicleToVehicleDto(vehicle));
        }

        return resultDtos;
    }

    @Override
    public ReservationDto createReservationByModel(ReservationCreateDto reservationCreateDto, Long modelId) {
        return null;
    }
}
