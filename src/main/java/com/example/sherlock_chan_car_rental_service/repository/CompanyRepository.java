package com.example.sherlock_chan_car_rental_service.repository;

import com.example.sherlock_chan_car_rental_service.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
