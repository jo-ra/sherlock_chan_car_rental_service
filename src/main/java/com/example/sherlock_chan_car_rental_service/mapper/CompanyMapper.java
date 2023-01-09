package com.example.sherlock_chan_car_rental_service.mapper;

import com.example.sherlock_chan_car_rental_service.domain.Address;
import com.example.sherlock_chan_car_rental_service.domain.Company;
import com.example.sherlock_chan_car_rental_service.dto.CompanyCreateDto;
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

    public Company companyCreateDtoToCompany(CompanyCreateDto companyCreateDto){
        Company company=new Company();
        company.setName(companyCreateDto.getName());
        company.setDescription(companyCreateDto.getDescription());
        company.setNumVehicls(companyCreateDto.getNumVehicles());
        //adresa
        Address address=new Address();
        address.setCountry(companyCreateDto.getAddressDto().getCountry());
        address.setCity(companyCreateDto.getAddressDto().getCity());
        address.setStreet(companyCreateDto.getAddressDto().getStreet());
        address.setPostcode(companyCreateDto.getAddressDto().getPostcode());
        company.setAddress(address);
        return company;
    }
}
