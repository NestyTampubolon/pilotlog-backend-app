package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("admin/addCompany")
    public ResponseEntity<Company> addCompany(@RequestBody Company company){
        return new ResponseEntity<>(companyService.addCompany(company), HttpStatus.CREATED);
    }

    @GetMapping("superadmin/company")
    public List<Company> getAllCompany(){
        return companyService.getAllCompany();
    }

    @GetMapping("public/company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") String idcompany){
        return new ResponseEntity<Company>(companyService.getCompanyById(idcompany), HttpStatus.OK);
    }

//    @PutMapping("company/update/{id}")
//    public ResponseEntity<Company> updateTraining(@PathVariable("id") String id,
//                                                        @RequestBody Company company){
//        return new ResponseEntity<Company>(companyService.updateCompany(company,id), HttpStatus.OK);
//    }

    @PutMapping(value = "admin/company/update/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable("id") String id,
                                                  @RequestBody Company company) {
        return new ResponseEntity<>(companyService.updateCompany(company, id), HttpStatus.OK);
    }

    @PutMapping(value = "superadmin/company/activation/{id}")
    public ResponseEntity<Company> activationCompany(@PathVariable("id") String id,
                                                  @RequestBody Company company) {
        return new ResponseEntity<>(companyService.activationCompany(company, id), HttpStatus.OK);
    }

    @PutMapping(value = "admin/company/update/logo/{id}")
    public ResponseEntity<Company> updateLogo(@PathVariable("id") String id,
                                                  @RequestBody MultipartFile logo) {
        return new ResponseEntity<>(companyService.updateLogo(logo, id), HttpStatus.OK);
    }

    @Value("${logo.directory}")
    private String logoDirectory;

    @GetMapping("images/{imageName}")
    public ResponseEntity<Resource> getImageLogo(@PathVariable String imageName) throws MalformedURLException {
        Path imagePath = Paths.get(logoDirectory).resolve(imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageResource);
    }

}
