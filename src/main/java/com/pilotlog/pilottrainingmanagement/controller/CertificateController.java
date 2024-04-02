package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Certificate;
import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping("addCertificate")
    public ResponseEntity<Certificate> addCertificate(@RequestBody Certificate certificate){
        return new ResponseEntity<>(certificateService.addCertificate(certificate), HttpStatus.CREATED);
    }

    @GetMapping("certificate")
    public ResponseEntity<Certificate> getCertificateById(){
        return new ResponseEntity<Certificate>(certificateService.getCertificateById(), HttpStatus.OK);
    }

    @PutMapping("certificate/update/{id}")
    public ResponseEntity<Certificate> updateCertificate(@PathVariable("id") String id,
                                           @RequestBody Certificate certificate){
        return new ResponseEntity<Certificate>(certificateService.updateCertificate(certificate,id), HttpStatus.OK);
    }

}
