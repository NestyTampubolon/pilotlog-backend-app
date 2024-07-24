package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.dto.AttendanceDetailRequest;
import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.model.AttendanceDetail;
import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.model.Statements;
import com.pilotlog.pilottrainingmanagement.service.AttendanceDetailService;
import com.pilotlog.pilottrainingmanagement.service.AttendanceService;
import com.pilotlog.pilottrainingmanagement.service.impl.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AttendanceDetailController {

    private final AttendanceDetailService attendanceDetailService;

//    @PostMapping("trainee/enrollattendance")
//    public ResponseEntity<AttendanceDetail> enrollAttendance(@RequestBody AttendanceDetail attendancedetail) throws ParseException {
//        return new ResponseEntity<>(attendanceDetailService.enrollAttendance(attendancedetail), HttpStatus.CREATED);
//    }

    // fungsi untuk enroll attendance oleh trainee
    @PostMapping("trainee/enrollattendance")
    public ResponseEntity<?> enrollAttendance(@RequestBody Attendance attendances) throws ParseException {
        try {
            Map<String, String> response = attendanceDetailService.enrollAttendance(attendances);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage()); // Set the exception message as the error message
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


    // mendapatkan data semua attendance detail berdasarkan id attendance
    @GetMapping("public/allattendancedetail/{id}")
    public List<AttendanceDetail> getAllAttendanceDetailByIdAttendance(@PathVariable("id") String idattendance){
        return attendanceDetailService.getAllAttendanceDetailByIdAttendance(idattendance);
    }

    // mendapatkan data attendance detail berdasarkan id attendance detail
    @GetMapping("public/attendancedetail/{id}")
    public ResponseEntity<AttendanceDetail> getAttendanceDetailByIdAttendance(@PathVariable("id") Long idattendancedetail){
        return new ResponseEntity<AttendanceDetail>(attendanceDetailService.getAttendanceDetailById(idattendancedetail), HttpStatus.OK);
    }

    // mendapatkan data attendance detail
    @GetMapping("public/allattendance/{id}")
    public List<AttendanceDetail> getAttendanceValidToByTrainingClass(@PathVariable("id") String id){
        return attendanceDetailService.getAttendanceValidToByTrainingClass(id);
    }

    // mendapatkan data attednance dan valid to nya
    @GetMapping("public/validation/{id}")
    public ResponseEntity<?> getAttendanceValidTo(@PathVariable("id") String id){
        return attendanceDetailService.getValidationPilot(id);
    }

    @GetMapping("public/validationallpilot")
    public ResponseEntity<?> getValidationAllPilot(){
        return attendanceDetailService.getValidationAllPilot();
    }


    // menambah grade trainee yang dilakukan oleh instructor
    @PutMapping("instructor/addgrade/{id}")
    public ResponseEntity<?>  addGradeAttendanceDetailById(@PathVariable("id") Long id,
                                                                         @RequestBody AttendanceDetailRequest request){
        return attendanceDetailService.addGradeAttendanceDetailById(request,id);
    }

    // menambah feedback yang dilakukan oleh trainee
    @PostMapping("trainee/addAssessment/{id}")
    public ResponseEntity<?>  addFeedbackAttendanceDetailById(@PathVariable("id") Long id,
                                                           @RequestBody AttendanceDetailRequest request){
        return attendanceDetailService.addFeedbackAttendanceDetailById(request,id);
    }

    // mengubah grade trainee yang dilakukan oleh instructor
    @PutMapping("instructor/updateattendancedetail/{id}")
    public ResponseEntity<?>  updateGradeAttendanceDetailById(@PathVariable("id") Long id,
                                                           @RequestBody AttendanceDetailRequest request){
        return attendanceDetailService.updateGradeAttendanceDetailById(request,id);
    }

    // menambah signature yang dilakukan oleh trainee
    @PutMapping("trainee/attendancedetailsiganture/{id}")
    public ResponseEntity<AttendanceDetail> addSignature(@PathVariable("id") Long id,
                                                                         @RequestBody byte[] signatureData){
        return new ResponseEntity<AttendanceDetail>(attendanceDetailService.addSignature(signatureData,id), HttpStatus.OK);
    }

    // mendapatkan attendance detail sesuai training class dan sesuai user trainee
    @GetMapping("public/attendancedetailbyidtrainingclass/{idTrainee}/{id}")
    public List<AttendanceDetail> getAttendanceDetailByIdTraineeaAndIdAttendanceClass(@PathVariable("id") String idtrainingclass, @PathVariable("idTrainee") String idTrainee){
        return attendanceDetailService.getAttendanceDetailByIdTraineeAndIdTrainingClass(idTrainee, idtrainingclass);
    }

    // mendapatkan data attendance detail dengan status pending pada attendance
    @GetMapping("trainee/attendancedetail/recentclass")
    public List<AttendanceDetail> getPendingAttendanceDetailsByTraineeId(){
        return attendanceDetailService.findPendingAttendanceDetailsByTraineeId();
    }

    // melihat status attendance yang dilakukan instructor
    @GetMapping("instructor/checkstatusattendance/{id}")
    public ResponseEntity<?> checkstatusattendance(@PathVariable("id") String id) throws ParseException{
        try {
            Map<String, String> response = attendanceDetailService.checkStatusAttendance(id);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage()); // Set the exception message as the error message
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // mendapatkan data attendance detail sesuai id trainee yng diakses oleh admin
    @GetMapping("admin/attendancedetailbyidtrainee/{id}")
    public List<AttendanceDetail> getAttendanceDetailByIdTrainee(@PathVariable("id") String id_users){
        return attendanceDetailService.findAttendanceDetailsByTraineeId(id_users);
    }



}
