package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Attendance;

import java.util.List;

public interface AttendanceService {
    Attendance addAttendance(Attendance attendance);
    List<Attendance> getAllAttendance();
    Attendance getAttendanceById(String id);
    Attendance updateAttendance(Attendance attendance, String id);
    Attendance deleteAttendance(Attendance attendance,String id);

    Attendance confirmationAttendancebyInstructor(Attendance attendance, String id);

    Attendance confirmationAttendancebyAdmin(Attendance attendance, String id);
}
