package com.example.sherlock_chan_car_rental_service.service.impl;

import com.example.sherlock_chan_car_rental_service.domain.Company;
import com.example.sherlock_chan_car_rental_service.dto.CompanyCreateDto;
import com.example.sherlock_chan_car_rental_service.dto.CompanyDto;
import com.example.sherlock_chan_car_rental_service.dto.CompanyUpdateDto;
import com.example.sherlock_chan_car_rental_service.exception.NotFoundException;
import com.example.sherlock_chan_car_rental_service.mapper.CompanyMapper;
import com.example.sherlock_chan_car_rental_service.repository.CompanyRepository;
import com.example.sherlock_chan_car_rental_service.security.CheckSecurity;
import com.example.sherlock_chan_car_rental_service.security.service.TokenService;
import com.example.sherlock_chan_car_rental_service.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImplementation implements CompanyService {

    private TokenService tokenService;
    private CompanyRepository companyRepository;
    private CompanyMapper companyMapper;

    public CompanyServiceImplementation(TokenService tokenService, CompanyRepository companyRepository, CompanyMapper companyMapper){
        this.tokenService = tokenService;
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public Page<CompanyDto> findAll(Pageable pageable) {
        return (Page<CompanyDto>) companyRepository.findAll(pageable).map(companyMapper::companyToCompanyDto);
    }

    @Override
    public CompanyDto updateCompany(Long id, CompanyUpdateDto companyUpdateDto) {
        Company company = companyRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Company with provided id %d has not been found",id)
                ));

        company.setAddress(companyMapper.companyUpdateDtoToAddress(companyUpdateDto));
        company.setDescription(companyUpdateDto.getDescription());
        company.setName(companyUpdateDto.getName());
        company.setNumVehicls(companyUpdateDto.getNumVehicles());
        company.setId(id);

        companyRepository.save(company);

        return companyMapper.companyToCompanyDto(companyRepository.save(company));
    }

    @Override
    public CompanyDto createCompany(CompanyCreateDto companyCreateDto) {
        Company company = companyMapper.companyCreateDtoToCompany(companyCreateDto);
        companyRepository.save(company);

        return companyMapper.companyToCompanyDto(company);
    }
}
