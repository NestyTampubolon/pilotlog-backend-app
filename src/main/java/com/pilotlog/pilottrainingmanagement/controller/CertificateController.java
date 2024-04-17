package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Certificate;
import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping("admin/addCertificate")
    public ResponseEntity<Certificate> addCertificate(@RequestBody Certificate certificate){
        return new ResponseEntity<>(certificateService.addCertificate(certificate), HttpStatus.CREATED);
    }

    @GetMapping("public/certificate")
    public ResponseEntity<Certificate> getCertificateById(){
        return new ResponseEntity<Certificate>(certificateService.getCertificateById(), HttpStatus.OK);
    }

    @PutMapping("admin/certificate/update/{id}")
    public ResponseEntity<Certificate> updateCertificate(@PathVariable("id") String id,
                                           @RequestBody Certificate certificate){
        return new ResponseEntity<Certificate>(certificateService.updateCertificate(certificate,id), HttpStatus.OK);
    }

    @PutMapping(value = "admin/certificate/update/background/{id}")
    public ResponseEntity<Certificate> updateBackgroundImage(@PathVariable("id") String id,
                                               @RequestBody MultipartFile backgroundImage) {
        return new ResponseEntity<>(certificateService.updateBackgroundCertificate(backgroundImage, id), HttpStatus.OK);
    }

    @PutMapping(value = "admin/certificate/update/signature/{id}")
    public ResponseEntity<Certificate> updateSignature(@PathVariable("id") String id,
                                                     @RequestBody MultipartFile signature) {
        return new ResponseEntity<>(certificateService.updateSignatureCertificate(signature, id), HttpStatus.OK);
    }

    @GetMapping("public/check/companycertificate")
    public boolean checkCertificateByCompany() {
        return certificateService.existsByCompany();
    }


    @Value("${certificate.directory}")
    private String certificateDirectory;

    @GetMapping("images/background/{imageName}")
    public ResponseEntity<Resource> getImageCertificate(@PathVariable String imageName) throws MalformedURLException {
        Path imagePath = Paths.get(certificateDirectory).resolve(imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageResource);
    }

    @Value("${cptssignature.directory}")
    private String cptssignatureDirectory;

    @GetMapping("images/cptssignature/{imageName}")
    public ResponseEntity<Resource> getCPTSSignature(@PathVariable String imageName) throws MalformedURLException {
        Path imagePath = Paths.get(cptssignatureDirectory).resolve(imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageResource);
    }


}
