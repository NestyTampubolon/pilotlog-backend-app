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

//    @Query(value = "SELECT at.id_instructor, AVG(a.rating) AS average_rating,  COUNT(at.id_attendance) AS total_attendance_count FROM assessments a JOIN statements s ON a.id_statements = s.id_statements JOIN attendancedetail ad ON ad.id_attendancedetail = a.id_attendancedetail RIGHT JOIN attendance at ON at.id_attendance = ad.id_attendance  JOIN trainingclass tc ON tc.id_trainingclass = at.id_trainingclass WHERE tc.id_company = :idCompany AND s.statement_type = 'forInstructor' AND at.is_delete = 0 GROUP BY at.id_instructor", nativeQuery = true)
//    List<?> getGradeInstructor(String idCompany);

    @Query(value = "SELECT at.id_instructor, u.photo_profile, u.name, AVG(a.rating) AS average_rating " +
            "FROM attendance at " +
            "LEFT JOIN attendancedetail ad ON at.id_attendance = ad.id_attendance " +
            "LEFT JOIN assessments a ON ad.id_attendancedetail = a.id_attendancedetail " +
            "LEFT JOIN statements s ON a.id_statements = s.id_statements " +
            "LEFT JOIN users u ON u.id_users = at.id_instructor " +
            "JOIN trainingclass tc ON tc.id_trainingclass = at.id_trainingclass " +
            "WHERE tc.id_company = :idCompany AND s.statement_type = 'forInstructor' AND at.is_delete = 0 " +
            "GROUP BY at.id_instructor " +
            "ORDER BY AVG(a.rating) DESC " +
            "LIMIT 5", nativeQuery = true)
    List<?> getGradeInstructor(String idCompany);



}
