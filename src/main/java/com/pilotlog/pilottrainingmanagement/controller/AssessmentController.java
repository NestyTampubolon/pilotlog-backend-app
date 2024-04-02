package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Assessments;
import com.pilotlog.pilottrainingmanagement.model.Attendance;
import com.pilotlog.pilottrainingmanagement.service.AssessmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentsService assessmentsService;

    @PostMapping("trainee/addAssessment")
    public ResponseEntity<Assessments> addAssessment(@RequestBody Assessments assessments) throws ParseException {
        return new ResponseEntity<>(assessmentsService.addAssessments(assessments), HttpStatus.CREATED);
    }

    @GetMapping("instructor/assessment/{id}")
    public ResponseEntity<Assessments> getAssessmentById(@PathVariable("id") Long idattendance){
        return new ResponseEntity(assessmentsService.getAssessmentByIdAttendenceDetail(idattendance), HttpStatus.OK);
    }
}
