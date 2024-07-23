package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.model.AttendanceDetail;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.service.AttendanceService;
import com.pilotlog.pilottrainingmanagement.service.TrainingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // form tambah attendance
    @PostMapping("admin/addAttendance")
    public ResponseEntity<Attendance> addAttendance(@RequestBody Attendance attendance) throws ParseException {
        return new ResponseEntity<>(attendanceService.addAttendance(attendance), HttpStatus.CREATED);
    }

    // get semua attendance
    @GetMapping("admin/attendance")
    public List<Attendance> getAllAttendance(){
        return attendanceService.getAllAttendance();
    }

    // get semua attendance yang sudah selesai
    @GetMapping("cpts/attendance/{id}")
    public List<Attendance> getAllAttendanceDone(@PathVariable("id") String idtrainingclass){
        return attendanceService.getAllAttendanceDone(idtrainingclass);
    }

    // get semua attendance berdasarkan tanggal
    @PostMapping("admin/attendance")
    public List<Attendance> getAllAttendanceByDate(@RequestBody Attendance attendance) throws ParseException {
        return attendanceService.getAllAttendanceByDate(attendance);
    }

    // get attendance berdasarkan id
    @GetMapping("public/attendance/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable("id") String idattendance){
        return new ResponseEntity<Attendance>(attendanceService.getAttendanceById(idattendance), HttpStatus.OK);
    }

    // update data attendance berdasarkan id
    @PutMapping("admin/attendance/update/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable("id") String id,
                                                        @RequestBody Attendance attendance) throws ParseException {
        return new ResponseEntity<Attendance>(attendanceService.updateAttendance(attendance,id), HttpStatus.OK);
    }

    // hapus attendance berdasarkan id
    @PutMapping("admin/attendance/delete/{id}")
    public ResponseEntity<Attendance> deleteAttendance(@PathVariable("id") String id,
                                                        @RequestBody Attendance attendance){
        return new ResponseEntity<Attendance>(attendanceService.deleteAttendance(attendance,id), HttpStatus.OK);
    }

    // konfirmasi attendance oleh admin
    @PutMapping("admin/attendance/confirmationAdmin/{id}")
    public ResponseEntity<Attendance> confirmationAttendancebyAdmin(@PathVariable("id") String id,
                                                       @RequestBody Attendance attendance){
        return new ResponseEntity<Attendance>(attendanceService.confirmationAttendancebyAdmin(attendance,id), HttpStatus.OK);
    }

    // confirmasi attendance oleh instructor
    @PutMapping("instructor/attendance/confirmationInstructor/{id}")
    public ResponseEntity<Attendance> confirmationAttendancebyInstructor(@PathVariable("id") String id,
                                                       @RequestBody Attendance attendance){
        return new ResponseEntity<Attendance>(attendanceService.confirmationAttendancebyInstructor(attendance,id), HttpStatus.OK);
    }

    // mendapatkan data attendance yang masih memiliki status pending atau yang sedang berlangsung oleh instructor
    @GetMapping("instructor/attendancependingbyinstructor")
    public List<Attendance> getattendancependingbyinstructor(){
        return attendanceService.getAttendancePendingByIdInstructor();
    }

    // mendapatkan data attendnace yang memiliki status done atau confirmation oleh instructor
    @GetMapping("instructor/attendanceconfirmationdonebyinstructor")
    public List<Attendance> getattendanceconfirmationdonebyinstructor(){
        return attendanceService.getAttendanceConfirmationDoneByIdInstructor();
    }

    // menambahkan data signature oleh  instructor di attendance
    @PutMapping("instructor/attendancesignature/{id}")
    public ResponseEntity<Attendance> addSignatureInstructor(@PathVariable("id") String id,
                                                         @RequestBody byte[] signatureData){
        return new ResponseEntity<Attendance>(attendanceService.addSignatureInstructor(signatureData,id), HttpStatus.OK);
    }

    @GetMapping("instructor/attendancebyidtrainingclass/{id}")
    public List<Attendance> getAttendanceDetailByIdInstructorAndIdTrainingClass(@PathVariable("id") String idtrainingclass){
        return attendanceService.getAttendanceByIdInstructorAndIdTrainingClass(idtrainingclass);
    }
}
