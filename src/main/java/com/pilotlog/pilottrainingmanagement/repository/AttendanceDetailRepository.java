package com.pilotlog.pilottrainingmanagement.repository;


import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.model.AttendanceDetail;
import com.pilotlog.pilottrainingmanagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceDetailRepository extends JpaRepository<AttendanceDetail, String> {

    @Query(value = "SELECT * FROM attendancedetail WHERE id_attendance = :idAttendance AND status != 'Pending'", nativeQuery = true)
    List<AttendanceDetail> findAllByIdAttendance(String idAttendance);

    AttendanceDetail findByIdTraineeAndIdAttendance(Users id_trainee, Attendance id_attendance);

    @Query(value= "SELECT ad.* FROM attendancedetail ad JOIN attendance a ON ad.id_attendance = a.id_attendance WHERE ad.id_trainee = :traineeId AND a.status = :status AND a.is_delete = 0", nativeQuery = true)
    List<AttendanceDetail> findAttendanceDetailsByTraineeIdAndStatus(String traineeId, String status);

    @Query(value= "SELECT ad.* FROM attendancedetail ad JOIN attendance a ON ad.id_attendance = a.id_attendance WHERE ad.id_trainee = :traineeId AND a.id_trainingclass = :id_trainingclass AND a.is_delete = 0 ORDER BY a.date DESC", nativeQuery = true)
    List<AttendanceDetail> findAttendanceDetailsByTraineeIdAndIdTrainingClass(String traineeId, String id_trainingclass);


    @Query(value= "SELECT ad.* FROM attendancedetail ad JOIN attendance a ON ad.id_attendance = a.id_attendance WHERE ad.id_trainee = :traineeId AND a.is_delete = 0 ORDER BY a.date DESC", nativeQuery = true)
    List<AttendanceDetail> findAttendanceDetailsByTraineeId(String traineeId);

}
