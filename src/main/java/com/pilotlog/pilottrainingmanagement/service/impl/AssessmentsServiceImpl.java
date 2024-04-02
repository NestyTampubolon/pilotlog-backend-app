package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Assessments;
import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.repository.AssessmentsRepository;
import com.pilotlog.pilottrainingmanagement.repository.StatementsRepository;
import com.pilotlog.pilottrainingmanagement.service.AssessmentsService;
import com.pilotlog.pilottrainingmanagement.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentsServiceImpl  implements AssessmentsService {
    private final AssessmentsRepository assessmentsRepository;
    @Override
    public Assessments addAssessments(Assessments assessments) {
        Assessments assessmentsC = new Assessments();
        assessmentsC.setId_statements(assessments.getId_statements());
        assessmentsC.setIdAttendanceDetail(assessments.getIdAttendanceDetail());
        assessmentsC.setRating(assessments.getRating());
        assessmentsC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        assessmentsC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        assessmentsC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        assessmentsC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return assessmentsRepository.save(assessmentsC);
    }

    @Override
    public List<Assessments> getAssessmentByIdAttendenceDetail(Long id) {
        return assessmentsRepository.findAllByidAttendanceDetail(id);
    }

    @Override
    public Assessments updateAssessments(Assessments assessments, String id) {
        return null;
    }

//    @Override
//    public Assessments addFeedback(Assessments assessments, String id) {
//        Room existingRoom = roomRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFoundException("Room", "Id", id)
//        );
//
//        existingRoom.setName(room.getName());
//        existingRoom.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
//        existingRoom.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
//        roomRepository.save(existingRoom);
//
//        return existingRoom;
//    }
}
