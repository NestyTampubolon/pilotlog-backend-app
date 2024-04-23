package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Assessments;
import com.pilotlog.pilottrainingmanagement.model.Statements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentsRepository extends JpaRepository<Assessments, String> {
    @Query(value = "SELECT a.* FROM assessments a JOIN statements s ON a.id_statements = s.id_statements WHERE a.id_attendancedetail = :idAttendanceDetail AND s.statement_type = 'forInstructor' ", nativeQuery = true)
    List<Assessments> findAllByidAttendanceDetailForInstructor(@Param("idAttendanceDetail") Long idAttendanceDetail);

    @Query(value = "SELECT a.* FROM assessments a JOIN statements s ON a.id_statements = s.id_statements JOIN attendancedetail ad ON ad.id_attendancedetail = a.id_attendancedetail WHERE ad.id_attendance = :idAttendance AND s.statement_type = 'forInstructor' ", nativeQuery = true)
    List<Assessments> findAllByidAttendanceForInstructor(@Param("idAttendance") String idAttendance);

    @Query(value = "SELECT a.* FROM assessments a JOIN statements s ON a.id_statements = s.id_statements WHERE a.id_attendancedetail = :idAttendanceDetail AND s.statement_type = 'forTrainee' ", nativeQuery = true)
    List<Assessments> findAllByidAttendanceDetailForTrainee(@Param("idAttendanceDetail") Long idAttendanceDetail);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM assessments a JOIN statements s ON a.id_statements = s.id_statements WHERE a.id_attendancedetail = :idAttendanceDetail AND s.statement_type = 'forInstructor' ", nativeQuery = true)
    int countAssessmentsForInstructorByAttendanceDetailId(String idAttendanceDetail);


}
