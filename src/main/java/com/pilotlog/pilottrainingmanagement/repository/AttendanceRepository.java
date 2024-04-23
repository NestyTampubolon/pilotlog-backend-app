package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    @Query(value= "SELECT ad.* FROM attendance ad JOIN trainingclass tc ON ad.id_trainingclass = tc.id_trainingclass  AND ad.is_delete = 0 AND tc.id_company = :idCompany ORDER BY ad.date DESC", nativeQuery = true)
    List<Attendance> findAllByIdCompany(String idCompany);
    @Query(value= "SELECT ad.* FROM attendance ad JOIN trainingclass tc ON ad.id_trainingclass  = tc.id_trainingclass  WHERE ad.is_delete = 0 AND tc.id_company = :idCompany AND ad.status = 'Done' AND ad.id_trainingclass = :idTrainingClass ORDER BY ad.date DESC", nativeQuery = true)
    List<Attendance> findAllDoneByIdCompany(String idCompany, String idTrainingClass);
    @Query(value= "SELECT ad.* FROM attendance ad JOIN trainingclass tc ON ad.id_trainingclass = tc.id_trainingclass  AND ad.is_delete = 0 AND ad.date = :date AND tc.id_company = :idCompany ", nativeQuery = true)
    List<Attendance> findAllByIdCompanyAndDate(String idCompany, Date date);
    Attendance findByKeyAttendance(String keyAttendance);
    @Query(value= "SELECT ad.* FROM attendance ad WHERE ad.id_instructor = :idInstructor AND ad.is_delete = 0 AND ad.status = 'Pending' ORDER BY ad.start_time ASC", nativeQuery = true)
    List<Attendance> getAttendancePendingByIdInstructor(String idInstructor);
    @Query(value= "SELECT ad.* FROM attendance ad WHERE ad.id_instructor = :idInstructor AND ad.is_delete = 0 AND (ad.status = 'Confirmation' OR ad.status = 'Done') ORDER BY ad.start_time ASC", nativeQuery = true)
    List<Attendance> getAttendanceConfirmationDoneByIdInstructor(String idInstructor);
    @Query(value= "SELECT ad.* FROM attendance ad WHERE ad.id_instructor = :idInstructor AND ad.is_delete = 0 AND ad.id_trainingclass = :idTrainingClass ORDER BY ad.start_time DESC", nativeQuery = true)
    List<Attendance> getAttendanceByIdInstructorAndIdTrainingClass(String idInstructor, String idTrainingClass);

}
