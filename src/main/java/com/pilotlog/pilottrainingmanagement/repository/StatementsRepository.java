package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.model.Statements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatementsRepository extends JpaRepository<Statements, String> {
    @Query(value = "SELECT * FROM statements WHERE id_company = :idCompany AND is_delete = 0", nativeQuery = true)
    List<Statements> findAllByCompanyIdAndIsDeleteIsZero(@Param("idCompany") String idCompany);

    @Query(value = "SELECT * FROM statements WHERE id_company = :idCompany AND is_delete = 0 AND statement_type = 'forInstructor'", nativeQuery = true)
    List<Statements> findAllStatementsForInstructor(@Param("idCompany") String idCompany);

    @Query(value = "SELECT * FROM statements WHERE id_company = :idCompany AND is_delete = 0 AND statement_type = 'forTrainee'", nativeQuery = true)
    List<Statements> findAllStatementsForTrainee(@Param("idCompany") String idCompany);
}
