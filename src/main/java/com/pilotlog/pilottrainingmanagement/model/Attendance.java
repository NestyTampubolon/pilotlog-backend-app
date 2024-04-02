package com.pilotlog.pilottrainingmanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
  // @GeneratedValue(strategy = GenerationType.UUID)
    private String id_attendance;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "venue", nullable = false)
    private String venue;

    @Column(name = "room", nullable = false)
    private String room;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "valid_to", nullable = false)
    private Date valid_to;

    @Column(name = "start_time", nullable = false)
    @JsonFormat(pattern="HH:mm")
    private Time start_time;

    @Column(name = "end_time", nullable = false)
    @JsonFormat(pattern="HH:mm")
    private Time end_time;

    @Column(name = "signature_instructor")
    private byte[] signature_instructor;

    @Column(name = "keyAttendance")
    private String keyAttendance;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "is_delete", nullable = false)
    private byte isDelete;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "created_by", nullable = false)
    private String created_by;

    @Column(name = "updated_by", nullable = false)
    private String updated_by;

    @ManyToOne
    @JoinColumn(name="id_instructor")
    private Users id_instructor;

    @ManyToOne
    @JoinColumn(name="id_admin")
    private Users id_admin;

    @ManyToOne
    @JoinColumn(name="id_trainingclass")
    private TrainingClass id_trainingclass;
}

