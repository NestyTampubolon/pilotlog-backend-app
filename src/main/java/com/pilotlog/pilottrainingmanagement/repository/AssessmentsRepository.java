package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Assessments;
import com.pilotlog.pilottrainingmanagement.model.Statements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentsRepository extends JpaRepository<Assessments, String> {
    @Query(value = "SELECT * FROM assessments WHERE id_attendancedetail = :idAttendanceDetail", nativeQuery = true)
    List<Assessments> findAllByidAttendanceDetail(@Param("idAttendanceDetail") Long idAttendanceDetail);
}
