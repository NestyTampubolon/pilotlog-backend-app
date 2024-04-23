package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Assessments;
import com.pilotlog.pilottrainingmanagement.model.AttendanceDetail;
import com.pilotlog.pilottrainingmanagement.model.Statements;
import com.pilotlog.pilottrainingmanagement.repository.AssessmentsRepository;
import com.pilotlog.pilottrainingmanagement.repository.AttendanceDetailRepository;
import com.pilotlog.pilottrainingmanagement.repository.StatementsRepository;
import com.pilotlog.pilottrainingmanagement.service.AssessmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AssessmentsServiceImpl  implements AssessmentsService {
    private final AssessmentsRepository assessmentsRepository;
    private final AttendanceDetailRepository attendanceDetailRepository;
    private final StatementsRepository statementsRepository;

    @Override
    public ResponseEntity<?> addGradeAttendanceDetailById(Map<String, Integer> ratings, Long id) {
        try {
            AttendanceDetail existingAttendanceDetail = attendanceDetailRepository.findById(String.valueOf(id)).orElseThrow(
                    () -> new ResourceNotFoundException("Attendance Detail", "Id", id)
            );
            // Menyimpan penilaian baru
            ratings.forEach((statement, ratingValue) -> {
                Statements statements = statementsRepository.findById(String.valueOf(statement)).orElseThrow(
                        () -> new ResourceNotFoundException("Statements", "id_statements", statement)
                );
                Assessments newAssessments = new Assessments();
                newAssessments.setId_statements(statements);
                newAssessments.setRating(ratingValue);
                newAssessments.setIdAttendanceDetail(existingAttendanceDetail);
                newAssessments.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
                newAssessments.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
                newAssessments.setCreated_by(AuthenticationServiceImpl.getUserInfo());
                newAssessments.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
                assessmentsRepository.save(newAssessments);
            });

            // Mengembalikan ResponseEntity dengan status 200 OK dan data yang diperbarui
            return ResponseEntity.ok().body("Penilaian berhasil ditambahkan");
        } catch (ResourceNotFoundException ex) {
            // Menangani pengecualian ResourceNotFoundException dengan mengembalikan status 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            // Menangani pengecualian umum dengan mengembalikan status 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan: " + ex.getMessage());
        }
    }


    @Override
    public List<Assessments> getAssessmentByIdAttendenceDetail(Long id) {
        return assessmentsRepository.findAllByidAttendanceDetailForInstructor(id);
    }

    @Override
    public List<Assessments> getAssessmentByIdAttendenceDetailForTrainee(Long id) {
        return assessmentsRepository.findAllByidAttendanceDetailForTrainee(id);
    }

    @Override
    public List<Assessments> findAllByidAttendanceForInstructor(String id) {
        return assessmentsRepository.findAllByidAttendanceForInstructor(id);
    }


    @Override
    public boolean existsAssessmentsByIdAttendanceDetail(String id) {
        int count = assessmentsRepository.countAssessmentsForInstructorByAttendanceDetailId(id);
        return count > 0;
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
