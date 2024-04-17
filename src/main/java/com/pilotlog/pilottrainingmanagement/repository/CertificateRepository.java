package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, String> {
    @Query(value = "SELECT * FROM certificate WHERE id_company = :idCompany", nativeQuery = true)
     Certificate findCertificateByCompanyId(@Param("idCompany") String idCompany);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM certificate c WHERE c.id_company = :companyId", nativeQuery = true)
    int existsByCompanyId(@Param("companyId") String companyId);

}
