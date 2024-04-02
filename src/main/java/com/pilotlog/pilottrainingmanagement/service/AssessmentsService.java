package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Assessments;
import com.pilotlog.pilottrainingmanagement.model.Room;

import java.util.List;

public interface AssessmentsService {
    Assessments addAssessments(Assessments assessments);
    List<Assessments> getAssessmentByIdAttendenceDetail(Long id);
    Assessments updateAssessments(Assessments assessments, String id);

//    Assessments addFeedback(Assessments assessments, String id);


}


