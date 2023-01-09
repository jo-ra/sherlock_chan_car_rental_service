package com.example.sherlock_chan_car_rental_service.mapper;

import com.example.sherlock_chan_car_rental_service.domain.Company;
import com.example.sherlock_chan_car_rental_service.dto.CompanyDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyDto companyToCompanyDto(Company company){
        CompanyDto companyDto=new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        companyDto.setDescription(company.getDescription());
        companyDto.setNumVehicles(company.getNumVehicls());
        return companyDto;
    }
}
