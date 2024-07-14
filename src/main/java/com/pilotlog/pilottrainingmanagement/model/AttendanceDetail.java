package com.pilotlog.pilottrainingmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import org.hibernate.annotations.Type;


@Data
@Entity
@Table(name = "attendancedetail")
public class AttendanceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_attendancedetail;

    @Lob
    @Column(name = "signature", columnDefinition = "BLOB")
    private byte[] signature;

    @Column(name = "no_certificate")
    private String no_Certificate;

    @Column(name = "grade")
    private String grade;

    @Column(name = "score")
    private Integer score;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @ManyToOne
    @JoinColumn(name="id_attendance")
    private Attendance idAttendance;

    @ManyToOne
    @JoinColumn(name="id_trainee")
    private Users idTrainee;
}
