package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Certificate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CertificateService {
    Certificate addCertificate(Certificate certificate);
    Certificate getCertificateById();
    Certificate updateCertificate(Certificate certificate, String id);
    Certificate updateBackgroundCertificate(MultipartFile background, String id);
    Certificate updateSignatureCertificate(MultipartFile signature, String id);
    boolean existsByCompany();

}
