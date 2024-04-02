package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Certificate;
import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.repository.CertificateRepository;
import com.pilotlog.pilottrainingmanagement.repository.RoomRepository;
import com.pilotlog.pilottrainingmanagement.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    @Override
    public Certificate addCertificate(Certificate certificate) {
        Certificate certificateC = new Certificate();
        certificateC.setId_cpts(certificate.getId_cpts());
        certificateC.setSignature(certificate.getSignature());
        certificateC.setId_company(AuthenticationServiceImpl.getCompanyInfo());
        certificateC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        certificateC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        certificateC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        certificateC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return certificateRepository.save(certificateC);
    }

    @Override
    public Certificate getCertificateById() {
        return certificateRepository.findCertificateByCompanyId(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

    @Override
    public Certificate updateCertificate(Certificate certificate, String id) {
        Certificate existingCertificate = certificateRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Certificate", "Id", id)
        );

        existingCertificate.setSignature(certificate.getSignature());
        existingCertificate.setId_cpts(certificate.getId_cpts());
        existingCertificate.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingCertificate.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        certificateRepository.save(existingCertificate);

        return existingCertificate;
    }
}
