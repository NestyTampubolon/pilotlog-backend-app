package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.dto.AttendanceDetailRequest;
import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.*;
import com.pilotlog.pilottrainingmanagement.repository.*;
import com.pilotlog.pilottrainingmanagement.service.AttendanceDetailService;
import com.pilotlog.pilottrainingmanagement.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
@Service
@RequiredArgsConstructor
public class AttendanceDetailServiceImpl implements AttendanceDetailService {
    private final AttendanceDetailRepository attendanceDetailRepository;
    private final AttendanceRepository attendanceRepository;
    private final AssessmentsRepository assessmentsRepository;
    private final StatementsRepository statementsRepository;
    private final UsersRepository usersRepository;
    private final CompanyService companyService;
    private final TrainingClassRepository trainingClassRepository;

    // enroll attendance yang dilakukan oleh trainee
    @Override
    public Map<String, String> enrollAttendance(Attendance attendances) throws ParseException {
        Map<String, String> response = new HashMap<>();

        // Retrieve the Attendance entity by keyAttendance
        Attendance attendance = attendanceRepository.findByKeyAttendance(attendances.getKeyAttendance());

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
                response.put("attendanceDetailId", String.valueOf(newAttendanceDetail.getId_attendancedetail()));
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

    // menambahkan signature yang dilakuken oleh trainee
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

    // mendapatkan semua attendance detail berdasarkan attendance
    @Override
    public List<AttendanceDetail> getAllAttendanceDetailByIdAttendance(String idAttendance) {
        return attendanceDetailRepository.findAllByIdAttendance(idAttendance);
    }

    // mendapatkan attendance detail berdasarkan id
    @Override
    public AttendanceDetail getAttendanceDetailById(Long id) {
        return attendanceDetailRepository.findById(String.valueOf(id)).orElseThrow(() -> new ResourceNotFoundException("Attendance", "Id", id));
    }

    // menambah grade attendance detail berdasarkan id
    @Override
    public ResponseEntity<?> addGradeAttendanceDetailById(AttendanceDetailRequest attendanceDetail, Long id) {
        try {
            AttendanceDetail existingAttendanceDetail = attendanceDetailRepository.findById(String.valueOf(id)).orElseThrow(
                    () -> new ResourceNotFoundException("Attendance Detail", "Id", id)
            );


            if(attendanceDetail.getAttendanceDetail().getScore() != null){
                existingAttendanceDetail.setScore(attendanceDetail.getAttendanceDetail().getScore());
            }
            existingAttendanceDetail.setGrade(attendanceDetail.getAttendanceDetail().getGrade());
            existingAttendanceDetail.setDescription(attendanceDetail.getAttendanceDetail().getDescription());
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

    // menambah feedback berdasarkan attedance detail
    @Override
    public ResponseEntity<?> addFeedbackAttendanceDetailById(AttendanceDetailRequest attendanceDetail, Long id) {
        try {
            AttendanceDetail existingAttendanceDetail = attendanceDetailRepository.findById(String.valueOf(id)).orElseThrow(
                    () -> new ResourceNotFoundException("Attendance Detail", "Id", id)
            );

            existingAttendanceDetail.setFeedback(attendanceDetail.getAttendanceDetail().getFeedback());
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


    // mengubah data grade yang dilakukan oleh instructor
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
            existingAttendanceDetail.setDescription(attendanceDetail.getAttendanceDetail().getDescription());
            existingAttendanceDetail.setStatus("Done");
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

    // melakukan cek status attendance
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

    // mendapatkan attendance detail berdasarkan id trainee
    @Override
    public List<AttendanceDetail> findAttendanceDetailsByTraineeId(String id) {
        return attendanceDetailRepository.findAttendanceDetailsByTraineeId(id);
    }

    // mendapatkan data attendance valid to berdasarkan training class
    @Override
    public List<AttendanceDetail> getAttendanceValidToByTrainingClass(String id) {
        return attendanceDetailRepository.findAttendanceValidToByTrainingClass(id);
    }

    LocalDate currentDate = LocalDate.now();


    // mendapatkan validasi pilot
    @Override
    public ResponseEntity<?> getValidationPilot(String id) {
        Users existingUsers = usersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Users", "Id", id)
        );


        List<TrainingClass> training = trainingClassRepository.findAllByIdCompany(AuthenticationServiceImpl.getCompanyInfo().getId_company());
        List<AttendanceDetail>  attendance = attendanceDetailRepository.findAttendanceValidToByTrainingClass(id);
        // List untuk menyimpan ID training yang ditemukan di attendance
        Set<String> foundTrainingIDs = new HashSet<>();

        // Variable untuk menyimpan jumlah data yang valid dan tidak valid
        int validCount = 0;
        int totalCount = 0;

        // Iterasi melalui daftar training
        for (TrainingClass train : training) {
            String trainingID = train.getId_trainingclass(); // Mendapatkan ID training
            totalCount++; // Menambahkan jumlah total training yang diperiksa

            // Memeriksa apakah training ID ada di daftar attendance
            boolean found = false;
            for (AttendanceDetail detail : attendance) {
                if (trainingID.equals(detail.getIdAttendance().getId_trainingclass().getId_trainingclass())) {
                    found = true;
                    LocalDate validTo = detail.getIdAttendance().getValid_to().toLocalDate(); // Mendapatkan tanggal valid_to
                    if (validTo.isAfter(currentDate)) {
//                        System.out.println("ID di Attendance: " + trainingID);
//                        System.out.println("Valid To : " + validTo + " (VALID)");
                        validCount++; // Menambahkan jumlah data yang valid
                    } else {
//                        System.out.println("ID di Attendance: " + trainingID);
//                        System.out.println("Valid To : " + validTo + " (NOT VALID)");
                    }
                    break;
                }
            }

            // Jika training ID tidak ditemukan di daftar attendance, berikan status FAIL
            if (!found) {
                System.out.println("ID di Attendance: " + trainingID);
                System.out.println("Status : FAIL");
                existingUsers.setStatus("NOT VALID");
            }

            // Menambahkan ID training ke dalam set foundTrainingIDs
            foundTrainingIDs.add(trainingID);
        }

            // Jika jumlah total training yang diperiksa sama dengan jumlah training yang ditemukan di attendance,
            // maka periksa apakah semua data memberikan status VALID
        if (totalCount == foundTrainingIDs.size()) {
            if (validCount == totalCount) {
                System.out.println("Status: PASS");
                existingUsers.setStatus("VALID");
            } else {
                System.out.println("Status: FAIL");
                existingUsers.setStatus("NOT VALID");
            }
        } else {
            System.out.println("Status: FAIL");
            existingUsers.setStatus("NOT VALID");
        }
        usersRepository.save(existingUsers);
        return ResponseEntity.ok().build();
    }

    // mendapatkan validasi semua pilot
    @Override
    public ResponseEntity<?> getValidationAllPilot(){

            List<Users> users = usersRepository.findAllPilotByCompanyId(AuthenticationServiceImpl.getCompanyInfo().getId_company());
            for(Users u : users){
                getValidationPilot(u.getId_users());
            }


        return ResponseEntity.ok().build();

    }


    // mendpaatkan data attendance detail berdasrkan status pending
    @Override
    public List<AttendanceDetail> findPendingAttendanceDetailsByTraineeId() {
        Users traineeId = AuthenticationServiceImpl.getUserProfileInfo();
        return attendanceDetailRepository.findAttendanceDetailsByTraineeIdAndStatus(traineeId.getId_users(), "Pending");
    }

//    @Override
//    public List<AttendanceDetail> getAttendanceDetailByIdTraineeAndIdTrainingClass(String idTrainingClass) {
//        Users traineeId = AuthenticationServiceImpl.getUserProfileInfo();
//        return attendanceDetailRepository.findAttendanceDetailsByTraineeIdAndIdTrainingClass(traineeId.getId_users(), idTrainingClass);
//    }


    // mendapatkan attendance detaol berdasarkan id trainee dan id training class
    @Override
    public List<AttendanceDetail> getAttendanceDetailByIdTraineeAndIdTrainingClass(String idTrainee, String idtrainingclass) {
        return attendanceDetailRepository.findAttendanceDetailsByTraineeIdAndIdTrainingClass(idTrainee, idtrainingclass);
    }
}
