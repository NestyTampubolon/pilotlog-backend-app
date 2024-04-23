package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.dto.AttendanceDetailRequest;
import com.pilotlog.pilottrainingmanagement.model.AttendanceDetail;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface AttendanceDetailService {

    Map<String, String> enrollAttendance(AttendanceDetail attendanceDetail) throws ParseException;
    AttendanceDetail addSignature(byte[] signatureData, Long id);
    List<AttendanceDetail> getAllAttendanceDetailByIdAttendance(String idAttendance);
    AttendanceDetail getAttendanceDetailById(Long id);
    ResponseEntity<?> addGradeAttendanceDetailById(AttendanceDetailRequest attendanceDetail, Long id);

    ResponseEntity<?> addFeedbackAttendanceDetailById(AttendanceDetailRequest attendanceDetail, Long id);

    ResponseEntity<?> updateGradeAttendanceDetailById(AttendanceDetailRequest attendanceDetail, Long id);
    List<AttendanceDetail> findPendingAttendanceDetailsByTraineeId();
    List<AttendanceDetail> getAttendanceDetailByIdTraineeAndIdTrainingClass(String idTrainee, String idtrainingclass);
    Map<String, String> checkStatusAttendance(String id);
    List<AttendanceDetail> findAttendanceDetailsByTraineeId(String id);
    List<AttendanceDetail> getAttendanceValidToByTrainingClass(String id);

}
