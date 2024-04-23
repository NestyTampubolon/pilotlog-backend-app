package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.dto.AttendanceDetailRequest;
import com.pilotlog.pilottrainingmanagement.model.Assessments;
import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.service.AssessmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentsService assessmentsService;

    @GetMapping("public/assessment/{id}")
    public ResponseEntity<Assessments> getAssessmentById(@PathVariable("id") Long idattendancedetail){
        return new ResponseEntity(assessmentsService.getAssessmentByIdAttendenceDetail(idattendancedetail), HttpStatus.OK);
    }

    @GetMapping("cpts/assessmentforTrainee/{id}")
    public ResponseEntity<Assessments> getAssessmentByIdForTrainee(@PathVariable("id") Long idattendancedetail){
        return new ResponseEntity(assessmentsService.getAssessmentByIdAttendenceDetailForTrainee(idattendancedetail), HttpStatus.OK);
    }


    @GetMapping("cpts/assessmentbyidattendance/{id}")
    public ResponseEntity<Assessments> getAssessmentByIdAttendance(@PathVariable("id") String idattendance){
        return new ResponseEntity(assessmentsService.findAllByidAttendanceForInstructor(idattendance), HttpStatus.OK);
    }

    @GetMapping("trainee/checkFeedback/{id}")
    public boolean checkFeedback(@PathVariable("id") String id) {
        return assessmentsService.existsAssessmentsByIdAttendanceDetail(id);
    }
}
