package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("addCompany")
    public ResponseEntity<Company> addCompany(@RequestBody Company company){
        return new ResponseEntity<>(companyService.addCompany(company), HttpStatus.CREATED);
    }

    @GetMapping("company")
    public List<Company> getAllCompany(){
        return companyService.getAllCompany();
    }

    @GetMapping("company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") String idcompany){
        return new ResponseEntity<Company>(companyService.getCompanyById(idcompany), HttpStatus.OK);
    }

    @PutMapping("company/update/{id}")
    public ResponseEntity<Company> updateTraining(@PathVariable("id") String id,
                                                        @RequestBody Company company){
        return new ResponseEntity<Company>(companyService.updateCompany(company,id), HttpStatus.OK);
    }
}
