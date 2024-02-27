package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    @Query(value = "SELECT * FROM department WHERE id_company = :idCompany AND is_delete = 0", nativeQuery = true)
    List<Department> findAllByCompanyIdAndIsDeleteIsZero(@Param("idCompany") String idCompany);

    @Query(value = "SELECT id_department FROM department WHERE id_company = :idCompany ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    String findLastIdByIdCompany(@Param("idCompany") String idCompany);
}
