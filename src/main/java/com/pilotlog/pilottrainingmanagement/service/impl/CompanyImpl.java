package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.repository.CompanyRepository;
import com.pilotlog.pilottrainingmanagement.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    @Override
    public Company addCompany(Company company) {
        Company companyC = new Company();
        companyC.setId_company(company.getId_company());
        companyC.setName(company.getName());
        companyC.setEmail(company.getEmail());
        companyC.setLogo(company.getLogo());
        companyC.setContact(company.getContact());
        companyC.setIs_delete((byte) 0);
        companyC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        companyC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        companyC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        companyC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return companyRepository.save(companyC);
    }

    @Override
    public List<Company> getAllCompany(){
        return companyRepository.findAll();
    }


    @Override
    public Company getCompanyById(String id) {
        return companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "Id", id));
    }


    @Override
    public Company updateCompany(Company company, String id) {

        Company existingCompany = companyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Company", "Id", id)
        );

        existingCompany.setName(company.getName());
        existingCompany.setEmail(company.getEmail());
        existingCompany.setLogo(company.getLogo());
        existingCompany.setContact(company.getContact());
        existingCompany.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingCompany.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        companyRepository.save(existingCompany);

        return existingCompany;
    }


}
