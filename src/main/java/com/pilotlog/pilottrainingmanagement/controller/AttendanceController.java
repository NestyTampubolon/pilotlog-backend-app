package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.service.AttendanceService;
import com.pilotlog.pilottrainingmanagement.service.TrainingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // build create Users
    @PostMapping("admin/addAttendance")
    public ResponseEntity<Attendance> addAttendance(@RequestBody Attendance attendance){
        return new ResponseEntity<>(attendanceService.addAttendance(attendance), HttpStatus.CREATED);
    }

    @GetMapping("admin/attendance")
    public List<Attendance> getAllAttendance(){
        return attendanceService.getAllAttendance();
    }

    @GetMapping("admin/attendance/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable("id") String idattendance){
        return new ResponseEntity<Attendance>(attendanceService.getAttendanceById(idattendance), HttpStatus.OK);
    }

    @PutMapping("admin/attendance/update/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable("id") String id,
                                                        @RequestBody Attendance attendance){
        return new ResponseEntity<Attendance>(attendanceService.updateAttendance(attendance,id), HttpStatus.OK);
    }

    @PutMapping("admin/attendance/delete/{id}")
    public ResponseEntity<Attendance> deleteAttendance(@PathVariable("id") String id,
                                                        @RequestBody Attendance attendance){
        return new ResponseEntity<Attendance>(attendanceService.deleteAttendance(attendance,id), HttpStatus.OK);
    }

    @PutMapping("admin/attendance/confirmationAdmin/{id}")
    public ResponseEntity<Attendance> confirmationAttendancebyAdmin(@PathVariable("id") String id,
                                                       @RequestBody Attendance attendance){
        return new ResponseEntity<Attendance>(attendanceService.confirmationAttendancebyAdmin(attendance,id), HttpStatus.OK);
    }

    @PutMapping("admin/attendance/confirmationInstructor/{id}")
    public ResponseEntity<Attendance> confirmationAttendancebyInstructor(@PathVariable("id") String id,
                                                       @RequestBody Attendance attendance){
        return new ResponseEntity<Attendance>(attendanceService.confirmationAttendancebyInstructor(attendance,id), HttpStatus.OK);
    }


}
