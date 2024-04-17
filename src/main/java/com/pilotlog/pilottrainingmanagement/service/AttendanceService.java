package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.model.AttendanceDetail;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

public interface AttendanceService {
    Attendance addAttendance(Attendance attendance) throws ParseException;
    List<Attendance> getAllAttendance();
    List<Attendance> getAllAttendanceByDate(Attendance attendance) throws ParseException;
    Attendance getAttendanceById(String id);
    Attendance updateAttendance(Attendance attendance, String id) throws ParseException;
    Attendance deleteAttendance(Attendance attendance,String id);
    Attendance confirmationAttendancebyInstructor(Attendance attendance, String id);
    Attendance confirmationAttendancebyAdmin(Attendance attendance, String id);
    List<Attendance> getAttendancePendingByIdInstructor();
    List<Attendance> getAttendanceConfirmationDoneByIdInstructor();
    Attendance addSignatureInstructor(byte[] signatureData, String id);
    List<Attendance> getAttendanceByIdInstructorAndIdTrainingClass(String id);
    Attendance updateDoneAttendance(String id);
}
