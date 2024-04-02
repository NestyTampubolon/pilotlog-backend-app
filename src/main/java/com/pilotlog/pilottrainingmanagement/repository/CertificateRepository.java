package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, String> {
    @Query(value = "SELECT * FROM certificate WHERE id_company = :idCompany", nativeQuery = true)
     Certificate findCertificateByCompanyId(@Param("idCompany") String idCompany);
}
