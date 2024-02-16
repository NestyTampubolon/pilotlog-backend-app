package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;

import java.util.List;

public interface CompanyService {
    Company addCompany(Company company);
    List<Company> getAllCompany();
    Company getCompanyById(String id);
    Company updateCompany(Company company, String id);


}
