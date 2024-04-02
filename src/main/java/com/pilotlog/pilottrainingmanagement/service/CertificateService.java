package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Certificate;
import com.pilotlog.pilottrainingmanagement.model.Room;

import java.util.List;

public interface CertificateService {
    Certificate addCertificate(Certificate certificate);
    Certificate getCertificateById();
    Certificate updateCertificate(Certificate certificate, String id);
}
