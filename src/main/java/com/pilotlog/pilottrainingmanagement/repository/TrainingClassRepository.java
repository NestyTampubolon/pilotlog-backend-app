package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.model.Certificate;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface TrainingClassRepository extends JpaRepository<TrainingClass, String> {

    @Query(value= "SELECT * FROM trainingclass WHERE is_delete = 0 AND id_company = :idCompany", nativeQuery = true)
    List<TrainingClass> findAllByIdCompany(String idCompany);


    @Query(value = "SELECT id_trainingclass FROM trainingclass WHERE id_company = :idCompany ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    String findLastIdByIdCompany(@Param("idCompany") String idCompany);

    @Query(value = "SELECT * FROM trainingclass WHERE id_trainingclass = :id_trainingclass AND id_company = :idCompany AND is_delete = 0", nativeQuery = true)
    List<TrainingClass> findByIdTrainingClass(@Param("id_trainingclass") String id_trainingclass, @Param("idCompany") String idCompany);

    @Query(value = "SELECT * FROM trainingclass WHERE id_company = :idCompany AND is_delete = 0", nativeQuery = true)
    List<TrainingClass> findTrainingClassByIdCompany(@Param("idCompany") String idCompany);
}

