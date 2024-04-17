package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.repository.CompanyRepository;
import com.pilotlog.pilottrainingmanagement.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
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

    public Company updateCompany(Company company, String id) {
        Company existingCompany = companyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Company", "Id", id)
        );

        System.out.println(company);

        existingCompany.setName(company.getName());
        existingCompany.setEmail(company.getEmail());
        existingCompany.setContact(company.getContact());
        existingCompany.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingCompany.setUpdated_by(AuthenticationServiceImpl.getUserInfo());


        // Simpan perusahaan ke database
        companyRepository.save(existingCompany);
        return existingCompany;
    }

    @Value("${logo.directory}")
    private String logoDirectory;

    @Override
    public Company updateLogo(MultipartFile logo, String id) {
        Company existingCompany = companyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Company", "Id", id)
        );
        if (logo != null && !logo.isEmpty()) {
            try {
                String logoFilename = saveLogo(logo);
                existingCompany.setLogo(logoFilename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save logo file.", e);
            }
        }
        companyRepository.save(existingCompany);
        return existingCompany;
    }

    private String saveLogo(MultipartFile file) throws IOException {
        // Generate unique filename
        String filename = UUID.randomUUID().toString() + ".png";
        // Simpan file ke folder assets
        Files.copy(file.getInputStream(), Paths.get(logoDirectory + filename), StandardCopyOption.REPLACE_EXISTING);
        // Kembalikan alamat file gambar
        return filename;
    }

    public Resource loadLogo(String filename) throws MalformedURLException {
        // Muat gambar dari folder assets
        Path file = Paths.get(logoDirectory + filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Failed to load file: " + filename);
        }
    }

}
