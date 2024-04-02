package com.pilotlog.pilottrainingmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "assessments")
public class Assessments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_assessments;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "created_by", nullable = false)
    private String created_by;

    @Column(name = "updated_by", nullable = false)
    private String updated_by;

    @ManyToOne
    @JoinColumn(name="id_attendancedetail")
    private AttendanceDetail idAttendanceDetail;

    @ManyToOne
    @JoinColumn(name="id_statements")
    private Statements id_statements;

}
