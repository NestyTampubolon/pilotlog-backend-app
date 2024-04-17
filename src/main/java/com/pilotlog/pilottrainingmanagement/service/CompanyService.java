package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Company;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService {
    Company addCompany(Company company);
    List<Company> getAllCompany();
    Company getCompanyById(String id);
    Company updateCompany(Company company, String id);
    Company updateLogo(MultipartFile logo, String id);
}
