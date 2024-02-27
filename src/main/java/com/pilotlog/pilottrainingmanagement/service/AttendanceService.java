package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Attendance;

import java.text.ParseException;
import java.util.List;

public interface AttendanceService {
    Attendance addAttendance(Attendance attendance) throws ParseException;
    List<Attendance> getAllAttendance();
    Attendance getAttendanceById(String id);
    Attendance updateAttendance(Attendance attendance, String id) throws ParseException;
    Attendance deleteAttendance(Attendance attendance,String id);

    Attendance confirmationAttendancebyInstructor(Attendance attendance, String id);

    Attendance confirmationAttendancebyAdmin(Attendance attendance, String id);
}
