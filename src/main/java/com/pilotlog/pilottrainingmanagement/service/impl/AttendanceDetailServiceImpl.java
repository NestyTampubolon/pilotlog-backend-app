package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.dto.AttendanceDetailRequest;
import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.*;
import com.pilotlog.pilottrainingmanagement.repository.AssessmentsRepository;
import com.pilotlog.pilottrainingmanagement.repository.AttendanceDetailRepository;
import com.pilotlog.pilottrainingmanagement.repository.AttendanceRepository;
import com.pilotlog.pilottrainingmanagement.repository.StatementsRepository;
import com.pilotlog.pilottrainingmanagement.service.AttendanceDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@Service
@RequiredArgsConstructor
public class AttendanceDetailServiceImpl implements AttendanceDetailService {
    private final AttendanceDetailRepository attendanceDetailRepository;
    private final AttendanceRepository attendanceRepository;
    private final AssessmentsRepository assessmentsRepository;
    private final StatementsRepository statementsRepository;

    @Override
    public Map<String, String> enrollAttendance(AttendanceDetail attendanceDetail) throws ParseException {
        Map<String, String> response = new HashMap<>();

        // Retrieve the Attendance entity by keyAttendance
        Attendance attendance = attendanceRepository.findByKeyAttendance(attendanceDetail.getKeyAttendance());

        // Check if attendance with provided keyAttendance exists
        if (attendance != null) {
            // Get the trainee ID
            Users traineeId = AuthenticationServiceImpl.getUserProfileInfo();

            // Check if there is an existing AttendanceDetail entry for the trainee and attendance
            AttendanceDetail existingAttendanceDetail = attendanceDetailRepository.findByIdTraineeAndIdAttendance(traineeId, attendance);

            // If no existing AttendanceDetail entry found, proceed with enrollment
            if (existingAttendanceDetail == null) {
                AttendanceDetail newAttendanceDetail = new AttendanceDetail();
                newAttendanceDetail.setIdTrainee(traineeId);
                newAttendanceDetail.setIdAttendance(attendance);
                newAttendanceDetail.setStatus("Pending");
                newAttendanceDetail.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
                newAttendanceDetail.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

                attendanceDetailRepository.save(newAttendanceDetail);

                response.put("status", "success");
                response.put("message", "Attendance enrolled successfully.");
            } else {
                response.put("status", "failed");
                response.put("message", "You have already enrolled for this attendance.");
            }
        } else {
            response.put("status", "failed");
            response.put("message", "Invalid keyAttendance");
        }

        return response;
    }

    @Override
    public AttendanceDetail addSignature(byte[] signatureData, Long id) {
        AttendanceDetail existingAttendanceDetail = attendanceDetailRepository.findById(String.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Attendance Detail", "Id", id)
        );

        existingAttendanceDetail.setStatus("Confirmation");
        existingAttendanceDetail.setSignature(signatureData);
        existingAttendanceDetail.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        attendanceDetailRepository.save(existingAttendanceDetail);

        return existingAttendanceDetail;
    }

    @Override
    public List<AttendanceDetail> getAllAttendanceDetailByIdAttendance(String idAttendance) {
        return attendanceDetailRepository.findAllByIdAttendance(idAttendance);
    }

    @Override
    public AttendanceDetail getAttendanceDetailById(Long id) {
        return attendanceDetailRepository.findById(String.valueOf(id)).orElseThrow(() -> new ResourceNotFoundException("Attendance", "Id", id));
    }
    public ResponseEntity<?> addGradeAttendanceDetailById(AttendanceDetailRequest attendanceDetail, Long id) {
        try {
            AttendanceDetail existingAttendanceDetail = attendanceDetailRepository.findById(String.valueOf(id)).orElseThrow(
                    () -> new ResourceNotFoundException("Attendance Detail", "Id", id)
            );


            if(attendanceDetail.getAttendanceDetail().getScore() != null){
                existingAttendanceDetail.setScore(attendanceDetail.getAttendanceDetail().getScore());
            }
            existingAttendanceDetail.setGrade(attendanceDetail.getAttendanceDetail().getGrade());
            existingAttendanceDetail.setStatus("Done");
            existingAttendanceDetail.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

            // Menyimpan penilaian baru
            attendanceDetail.getRatings().forEach((statement, ratingValue) -> {
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

            // Menyimpan perubahan pada AttendanceDetail yang ada
            attendanceDetailRepository.save(existingAttendanceDetail);

            // Mengembalikan ResponseEntity dengan status 200 OK dan data yang diperbarui
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // Menangani pengecualian ResourceNotFoundException dengan mengembalikan status 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            // Menangani pengecualian umum dengan mengembalikan status 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan: " + ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateGradeAttendanceDetailById(AttendanceDetailRequest attendanceDetail, Long id) {
        try {
            AttendanceDetail existingAttendanceDetail = attendanceDetailRepository.findById(String.valueOf(id)).orElseThrow(
                    () -> new ResourceNotFoundException("Attendance Detail", "Id", id)
            );


            if(attendanceDetail.getAttendanceDetail().getScore() != null){
                existingAttendanceDetail.setScore(attendanceDetail.getAttendanceDetail().getScore());
            }
            existingAttendanceDetail.setGrade(attendanceDetail.getAttendanceDetail().getGrade());
            existingAttendanceDetail.setStatus("Confirmation");
            existingAttendanceDetail.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

            // Menyimpan penilaian baru
            attendanceDetail.getRatings().forEach((idassessments, ratingValue) -> {
                Assessments assessments = assessmentsRepository.findById(String.valueOf(idassessments)).orElseThrow(
                        () -> new ResourceNotFoundException("Statements", "id_statements", idassessments)
                );
                assessments.setRating(ratingValue);
                assessments.setIdAttendanceDetail(existingAttendanceDetail);
                assessments.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
                assessments.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
                assessmentsRepository.save(assessments);
            });

            // Menyimpan perubahan pada AttendanceDetail yang ada
            attendanceDetailRepository.save(existingAttendanceDetail);

            // Mengembalikan ResponseEntity dengan status 200 OK dan data yang diperbarui
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // Menangani pengecualian ResourceNotFoundException dengan mengembalikan status 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            // Menangani pengecualian umum dengan mengembalikan status 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan: " + ex.getMessage());
        }
    }

    @Override
    public Map<String, String> checkStatusAttendance(String id) {
        Map<String, String> response = null;
        try {
            response = new HashMap<>();
            List<AttendanceDetail> attendanceDetails = attendanceDetailRepository.findAllByIdAttendance(id);

            // Memeriksa apakah semua status tidak bernilai null
            boolean allStatusNotNull = attendanceDetails.stream()
                    .allMatch(attendanceDetail -> attendanceDetail.getGrade() != null);

            // Jika semua status tidak bernilai null, kembalikan true
            if (allStatusNotNull) {
                response.put("status", "true");
            } else {
                // Jika ada setidaknya satu status yang bernilai null, kembalikan false
                response.put("status", "false");
                response.put("message", "You have not entered all the values.");
            }
            return response;
        } catch (Exception e) {
            // Tangani kesalahan dengan memberikan respons status 500 (Internal Server Error)
            response.put("status", "false");
            response.put("message", "You have not entered all the values.");
            return response;
        }
    }





    @Override
    public List<AttendanceDetail> findPendingAttendanceDetailsByTraineeId() {
        Users traineeId = AuthenticationServiceImpl.getUserProfileInfo();
        return attendanceDetailRepository.findAttendanceDetailsByTraineeIdAndStatus(traineeId.getId_users(), "Pending");
    }

    @Override
    public List<AttendanceDetail> getAttendanceDetailByIdTraineeAndIdTrainingClass(String idTrainingClass) {
        Users traineeId = AuthenticationServiceImpl.getUserProfileInfo();
        return attendanceDetailRepository.findAttendanceDetailsByTraineeIdAndIdTrainingClass(traineeId.getId_users(), idTrainingClass);
    }
}
