package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.model.Status;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.repository.AttendanceRepository;
import com.pilotlog.pilottrainingmanagement.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Override
    public Attendance addAttendance(Attendance attendance) {
        Attendance attendanceC = new Attendance();
        attendanceC.setId_attendance(attendance.getId_attendance());
        attendanceC.setDepartment(attendance.getDepartment());
        attendanceC.setVenue(attendance.getVenue());
        attendanceC.setRoom(attendance.getRoom());
        attendanceC.setDate(attendance.getDate());
        attendanceC.setValid_to(attendance.getValid_to());
        attendanceC.setStart_time(attendance.getStart_time());
        attendanceC.setEnd_time(attendance.getEnd_time());
        attendanceC.setStatus(Status.valueOf("Pending"));
        attendanceC.setKeyAttendance(getSaltString());
        attendanceC.setIs_delete((byte) 0);
        attendanceC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        attendanceC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        attendanceC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        attendanceC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return attendanceRepository.save(attendanceC);
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @Override
    public Attendance getAttendanceById(String id) {
        return attendanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendance", "Id", id));
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 11) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    @Override
    public Attendance updateAttendance(Attendance attendance, String id) {
        Attendance existingAttendance = attendanceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Attendance", "Id", id)
        );

        existingAttendance.setDepartment(attendance.getDepartment());
        existingAttendance.setVenue(attendance.getVenue());
        existingAttendance.setRoom(attendance.getRoom());
        existingAttendance.setDate(attendance.getDate());
        existingAttendance.setValid_to(attendance.getValid_to());
        existingAttendance.setStart_time(attendance.getStart_time());
        existingAttendance.setEnd_time(attendance.getEnd_time());
        existingAttendance.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingAttendance.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        attendanceRepository.save(existingAttendance);

        return existingAttendance;
    }

    @Override
    public Attendance deleteAttendance(Attendance attendance, String id) {

        Attendance existingAttandance = attendanceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Attendance", "Id", id)
        );

        existingAttandance.setIs_delete((byte) 1);
        return existingAttandance;
    }

    @Override
    public Attendance confirmationAttendancebyInstructor(Attendance attendance, String id) {
        Attendance existingAttandance = attendanceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Attendance", "Id", id)
        );

        existingAttandance.setStatus(Status.valueOf("Confirmation"));
        existingAttandance.setSignature_instructor(attendance.getSignature_instructor());
        return existingAttandance;
    }

    @Override
    public Attendance confirmationAttendancebyAdmin(Attendance attendance, String id) {
        Attendance existingAttandance = attendanceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Attendance", "Id", id)
        );

        existingAttandance.setStatus(Status.valueOf("Done"));
        return existingAttandance;
    }


}
