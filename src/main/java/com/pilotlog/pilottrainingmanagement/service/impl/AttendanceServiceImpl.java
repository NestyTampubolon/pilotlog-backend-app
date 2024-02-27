package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.model.Status;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.repository.AttendanceRepository;
import com.pilotlog.pilottrainingmanagement.service.AttendanceService;
import com.pilotlog.pilottrainingmanagement.service.JWTService;
import com.pilotlog.pilottrainingmanagement.service.TrainingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Autowired
    private final TrainingClassService trainingClassService;

    @Override
    public Attendance addAttendance(Attendance attendance) throws ParseException {
        Attendance attendanceC = new Attendance();
        LocalDateTime now = LocalDateTime.now();

        // Menggunakan format tertentu untuk LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = trainingClassService.getValueDateTrainingClass(attendance.getId_trainingclass().getId_trainingclass(), attendance.getDate());
        if(dateStr != null){
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

            java.util.Date utilEndTime = sdfTime.parse(String.valueOf(attendance.getEnd_time()));
            Time endTime = new Time(utilEndTime.getTime());
            System.out.println(endTime);

            java.util.Date utilStartTime = sdfTime.parse(String.valueOf(attendance.getEnd_time()));
            Time startTime = new Time(utilStartTime.getTime());
            System.out.println(startTime);


            attendanceC.setId_attendance(AuthenticationServiceImpl.getUserInfo() + attendance.getId_trainingclass().getId_trainingclass() + formattedDateTime);
            attendanceC.setDepartment(attendance.getDepartment());
            attendanceC.setVenue(attendance.getVenue());
            attendanceC.setRoom(attendance.getRoom());
            attendanceC.setDate(attendance.getDate());
            attendanceC.setValid_to(Date.valueOf(dateStr));
            attendanceC.setStart_time(startTime);
            attendanceC.setEnd_time(endTime);
            attendanceC.setId_instructor(attendance.getId_instructor());
            attendanceC.setId_admin(AuthenticationServiceImpl.getUserAdminInfo());
            attendanceC.setStatus(Status.valueOf("Pending"));
            attendanceC.setKeyAttendance(getSaltString());
            attendanceC.setIsDelete((byte) 0);
            attendanceC.setId_trainingclass(attendance.getId_trainingclass());
            attendanceC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
            attendanceC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
            attendanceC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
            attendanceC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
            return attendanceRepository.save(attendanceC);
        }
        return null;
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAllByIsDelete((byte) 0);
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
    public Attendance updateAttendance(Attendance attendance, String id) throws ParseException {
        Attendance existingAttendance = attendanceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Attendance", "Id", id)
        );

        String dateStr = trainingClassService.getValueDateTrainingClass(attendance.getId_trainingclass().getId_trainingclass(), attendance.getDate());
        if(dateStr != null){

            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

            java.util.Date utilEndTime = sdfTime.parse(String.valueOf(attendance.getEnd_time()));
            Time endTime = new Time(utilEndTime.getTime());
            System.out.println(endTime);

            java.util.Date utilStartTime = sdfTime.parse(String.valueOf(attendance.getEnd_time()));
            Time startTime = new Time(utilStartTime.getTime());
            System.out.println(startTime);


            existingAttendance.setId_trainingclass(attendance.getId_trainingclass());
            existingAttendance.setDepartment(attendance.getDepartment());
            existingAttendance.setVenue(attendance.getVenue());
            existingAttendance.setRoom(attendance.getRoom());
            existingAttendance.setDate(attendance.getDate());
            existingAttendance.setValid_to(Date.valueOf(dateStr));
            existingAttendance.setStart_time(startTime);
            existingAttendance.setEnd_time(endTime);
            existingAttendance.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
            existingAttendance.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
            attendanceRepository.save(existingAttendance);
        }

        return existingAttendance;
    }

    @Override
    public Attendance deleteAttendance(Attendance attendance, String id) {
        Attendance existingAttandance = attendanceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Attendance", "Id", id)
        );
        existingAttandance.setIsDelete((byte) 1);
        attendanceRepository.save(existingAttandance);
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
