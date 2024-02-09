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

    @Column(name = "grade", nullable = false)
    private int grade;

    @Column(name = "fromAudience", nullable = false)
    private byte fromAudience;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @ManyToOne
    @JoinColumn(name="id_attendancedetail")
    private AttendanceDetail id_attendancedetail;

    @ManyToOne
    @JoinColumn(name="id_statements")
    private Statements id_statements;

}
