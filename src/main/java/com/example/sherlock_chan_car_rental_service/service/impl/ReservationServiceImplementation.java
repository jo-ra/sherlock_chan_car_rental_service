package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.domain.Company;
import com.example.sherlock_chan_car_rental_service.domain.Reservation;
import com.example.sherlock_chan_car_rental_service.domain.Vehicle;
import com.example.sherlock_chan_car_rental_service.dto.*;
import com.example.sherlock_chan_car_rental_service.exception.NotFoundException;
import com.example.sherlock_chan_car_rental_service.mapper.CompanyMapper;
import com.example.sherlock_chan_car_rental_service.mapper.ModelMapper;
import com.example.sherlock_chan_car_rental_service.mapper.ReservationMapper;
import com.example.sherlock_chan_car_rental_service.mapper.VehicleMapper;
import com.example.sherlock_chan_car_rental_service.repository.CompanyRepository;
import com.example.sherlock_chan_car_rental_service.repository.ReservationRepository;
import com.example.sherlock_chan_car_rental_service.repository.VehicleRepository;
import com.example.sherlock_chan_car_rental_service.service.ReservationService;
import com.example.sherlock_chan_car_rental_service.user_service.dtos.ClientDto;
import com.example.sherlock_chan_car_rental_service.user_service.dtos.DiscountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ReservationServiceImplementation implements ReservationService {

    private ReservationMapper reservationMapper;
    private VehicleMapper vehicleMapper;
    private CompanyMapper companyMapper;

    private ReservationRepository reservationRepository;
    private CompanyRepository companyRepository;
    private VehicleRepository vehicleRepository;

    private RestTemplate userServiceRestTemplate;

    public ReservationServiceImplementation(ReservationMapper reservationMapper, CompanyRepository companyRepository,
                                            ReservationRepository reservationRepository, VehicleRepository vehicleRepository,
                                            VehicleMapper vehicleMapper, RestTemplate userServiceRestTemplate,
                                            CompanyMapper companyMapper){
        this.reservationMapper = reservationMapper;
        this.companyRepository=companyRepository;
        this.reservationRepository = reservationRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.userServiceRestTemplate = userServiceRestTemplate;
        this.companyMapper = companyMapper;
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
    public ReservationDto createReservationByModel(ReservationCreateByModelDto reservationCreateByModelDto) {
        LocalDate start_date = reservationCreateByModelDto.getStarting_date();
        LocalDate end_date = reservationCreateByModelDto.getEnding_date();
        Long modelId = reservationCreateByModelDto.getModel_id();
        Long company_id = reservationCreateByModelDto.getCompany_id();

        System.out.println("USER ID " + reservationCreateByModelDto.getUser_id());

        int user_discount = getDiscountByUserId(reservationCreateByModelDto.getUser_id());

        List<Vehicle> availableVehicles = listAvailableVehiclesNew(start_date, end_date)
                .stream()
                .map(vehicleMapper::vehicleDtoToVehicle)
                .collect(Collectors.toList());

        boolean isAvailable = false;

        Vehicle vehicleToReserve = null;

        for(Vehicle vehicle : availableVehicles){
            if((vehicle.getModel().getId().equals(modelId)) && (vehicle.getCompany().getId().equals(company_id))){
                isAvailable = true;
                vehicleToReserve = vehicle;
                break;
            }
        }

        if(isAvailable){
            long datesDiff = ChronoUnit.DAYS.between(start_date, end_date);

            double total_price = 0.0;
            double per_day = vehicleToReserve.getModel().getPrice();

            if(user_discount != 0){
                total_price = ((100 - user_discount) * per_day * datesDiff) / 100;
            }else{
                total_price = per_day * datesDiff;
            }

            Reservation reservation = new Reservation();

            Company company = companyRepository.findById(company_id)
                    .orElseThrow(() -> new NotFoundException(
                            String.format("Company with provided id %d cannot be found", company_id)
                    ));

            reservation.setUser_id(reservationCreateByModelDto.getUser_id());
            reservation.setCompany(company);
            reservation.setIs_active(1);
            reservation.setTotal_price(total_price);
            reservation.setStarting_date(start_date);
            reservation.setEnding_date(end_date);
            reservation.setVehicle(vehicleToReserve);

            callIncrease(reservationCreateByModelDto.getUser_id());

            return reservationMapper.reservationToReservationDto(reservationRepository.save(reservation));
        }else{
            System.out.println("Cannot make reservation");
            return null;
        }
    }

    @Override
    public ReservationDto createReservationByType(ReservationCreateByTypeDto reservationCreateByTypeDto) {

        /** FUNKCIJA KOJA TREBA DA SE ZAVRSI
         *
         *  Korisnik u reservationCreateDto salje i userid, i vehicle id i company id i datum
         *  Treba da docupam listu dostupnih vozila i da proverim da li se vozilo sa typeId
         *  i sa kompani id iz createDto nalazi u listi dostupnih vozila. Ako se nalazi,
         *  izvrsiti kreiranje rezervacije.
         *
         * */
        LocalDate start_date = reservationCreateByTypeDto.getStarting_date();
        LocalDate end_date = reservationCreateByTypeDto.getEnding_date();
        Long typeId = reservationCreateByTypeDto.getType_id();
        Long company_id = reservationCreateByTypeDto.getCompany_id();

//        System.out.println("USER ID " + reservationCreateDto.getUser_id());

        int user_discount = getDiscountByUserId(reservationCreateByTypeDto.getUser_id());

        List<Vehicle> availableVehicles = listAvailableVehiclesNew(start_date, end_date)
                .stream()
                .map(vehicleMapper::vehicleDtoToVehicle)
                .collect(Collectors.toList());

        boolean isAvailable = false;

        Vehicle vehicleToReserve = null;

        for(Vehicle vehicle : availableVehicles){
            // treba razmotriti da li se rezervacija po tipu odnosi na to da nadjem prvi slobodan tip
            // za ovaj termin bez obzira na kompaniju
            if((vehicle.getType().getId().equals(typeId)) && vehicle.getCompany().getId().equals(company_id)){
                isAvailable = true;
                vehicleToReserve = vehicle;
                break;
            }
        }

        if(isAvailable){
            long datesDiff = ChronoUnit.DAYS.between(start_date, end_date);

            double total_price = 0.0;
            double per_day = vehicleToReserve.getModel().getPrice();

//            System.out.println(vehicleToReserve.getModel().getPrice());

            if(user_discount != 0){
                total_price = ((100 - user_discount) * per_day * datesDiff) / 100;
            }else{
                total_price = per_day * datesDiff;
            }

            Reservation reservation = new Reservation();
            Company company = companyRepository.findById(company_id).orElseThrow(() -> new NotFoundException(
                    String.format("Company with id %d not found", company_id)
            ));

            reservation.setUser_id(reservationCreateByTypeDto.getUser_id());
            reservation.setCompany(company);
            reservation.setIs_active(1);
            reservation.setTotal_price(total_price);
            reservation.setStarting_date(start_date);
            reservation.setEnding_date(end_date);
            reservation.setVehicle(vehicleToReserve);

            // Povecati korisniku broj dosadasnjih iznajmljivanja
            callIncrease(reservation.getUser_id());

            return reservationMapper.reservationToReservationDto(reservationRepository.save(reservation));
        }else{
            System.out.println("Cannot make reservation !");
            return null;
        }
    }

    @Override
    public ReservationDto cancelReservation(Long reservation_id) {
        Reservation reservation = reservationRepository
                .findById(reservation_id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Reservation with provided id %d cannot be found", reservation_id)
                ));

        System.out.println(reservation.getId());

        reservationRepository.delete(reservation);

        return reservationMapper.reservationToReservationDto(reservation);
    }

    private void callIncrease(Long user_id){
        ResponseEntity<ClientDto> userResponseEntity = null;
        try{
            userResponseEntity = userServiceRestTemplate.exchange("/user/increaseReservations/" + user_id, HttpMethod.GET, null, ClientDto.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                throw new NotFoundException(String.format("User with id %d not found " , user_id));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int getDiscountByUserId(Long user_id){
        System.out.println(user_id);
        ResponseEntity<DiscountDto> discountResponseEntity = null;
        try{
            discountResponseEntity = userServiceRestTemplate.exchange("/user/discount/" + user_id, HttpMethod.GET, null, DiscountDto.class);
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                throw new NotFoundException(String.format("User with id %d not found ", user_id));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return discountResponseEntity.getBody().getDiscount();
    }


    public List<VehicleDto> listAvailableVehicles(){
        List<Vehicle> vehiclesResultSet = vehicleRepository.findAll();
        List<Reservation> reservationsResultSet = reservationRepository.findAll();
        List<Vehicle> resultVehicles = new ArrayList<>();
        List<VehicleDto> resultDtos = new ArrayList<>();

        System.out.println(reservationsResultSet.size());

        for(Reservation reservation : reservationsResultSet){
            if(!resultVehicles.contains(reservation.getVehicle()))
                resultVehicles.add(reservation.getVehicle());
        }

        vehiclesResultSet.removeAll(resultVehicles);

        for(Vehicle vehicle : vehiclesResultSet){
            resultDtos.add(vehicleMapper.vehicleToVehicleDto(vehicle));
        }

        return resultDtos;
    }

    public List<VehicleDto> listAvailableVehiclesNew(LocalDate startDate, LocalDate endDate){
        List<Vehicle> vehiclesResultSet = vehicleRepository.findAll();
        List<Reservation> reservationsResultSet = reservationRepository.findAll();

        for(Reservation reservation : reservationsResultSet){
            Vehicle vehicle = reservation.getVehicle();
            boolean res = checkDateRange(reservation.getStarting_date(), startDate);

            if(res){
                vehiclesResultSet.remove(vehicle);
            }
        }

        return vehiclesResultSet
                .stream()
                .map(vehicleMapper::vehicleToVehicleDto)
                .collect(Collectors.toList());
    }

    private boolean checkDateRange(LocalDate res_start_date, LocalDate user_start_date){
        if(res_start_date.isEqual(user_start_date)){
            return true;
        }
        return false;
    }
}
