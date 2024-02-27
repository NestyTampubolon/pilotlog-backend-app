package com.pilotlog.pilottrainingmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "attendanceDetail")
public class AttendanceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_attendancedetail;

    @Column(name = "signature", nullable = false)
    private String signature;

    @Column(name = "no_certificate", nullable = false)
    private String no_Certificate;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @ManyToOne
    @JoinColumn(name="id_attendance")
    private Attendance id_attendance;

    @ManyToOne
    @JoinColumn(name="id_trainee")
    private Users id_trainee;
}
