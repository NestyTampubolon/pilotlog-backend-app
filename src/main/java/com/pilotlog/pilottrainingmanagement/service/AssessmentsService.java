package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Assessments;
import com.pilotlog.pilottrainingmanagement.model.Room;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AssessmentsService {
    ResponseEntity<?> addGradeAttendanceDetailById(Map<String, Integer> ratings, Long id);

    List<Assessments> getAssessmentByIdAttendenceDetail(Long id);

    List<Assessments> getAssessmentByIdAttendenceDetailForTrainee(Long id);

    List<Assessments> findAllByidAttendanceForInstructor(String id);

    boolean existsAssessmentsByIdAttendanceDetail(String id);

    Assessments updateAssessments(Assessments assessments, String id);

//    Assessments addFeedback(Assessments assessments, String id);




}


