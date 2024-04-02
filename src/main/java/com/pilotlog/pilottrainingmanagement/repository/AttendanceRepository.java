package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.model.AttendanceDetail;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    List<Attendance> findAllByIsDelete(byte isDelete);
    Attendance findByKeyAttendance(String keyAttendance);

    @Query(value= "SELECT ad.* FROM attendance ad WHERE ad.id_instructor = :idInstructor AND ad.is_delete = 0 AND ad.status = 'Pending' ORDER BY ad.start_time ASC", nativeQuery = true)
    List<Attendance> getAttendancePendingByIdInstructor(String idInstructor);

    @Query(value= "SELECT ad.* FROM attendance ad WHERE ad.id_instructor = :idInstructor AND ad.is_delete = 0 AND (ad.status = 'Confirmation' OR ad.status = 'Done') ORDER BY ad.start_time ASC", nativeQuery = true)
    List<Attendance> getAttendanceConfirmationDoneByIdInstructor(String idInstructor);


}
