package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Department;
import com.pilotlog.pilottrainingmanagement.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, String> {
    @Query(value = "SELECT * FROM venue WHERE id_company = :idCompany AND is_delete = 0", nativeQuery = true)
    List<Venue> findAllByCompanyIdAndIsDeleteIsZero(@Param("idCompany") String idCompany);

}
