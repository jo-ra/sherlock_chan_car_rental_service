package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.dto.CompanyDto;
import com.example.sherlock_chan_car_rental_service.dto.CompanyUpdateDto;
import com.example.sherlock_chan_car_rental_service.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImplementation implements CompanyService {
    @Override
    public Page<CompanyDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CompanyDto updateCompany(Long id, CompanyUpdateDto companyUpdateDto) {
        return null;
    }
}
