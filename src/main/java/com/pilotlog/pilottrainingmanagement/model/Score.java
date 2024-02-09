package com.pilotlog.pilottrainingmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_score;

    @Column(name = "grade", nullable = false)
    private int grade;

    @Column(name = "status")
    private StatusScore status;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @ManyToOne
    @JoinColumn(name="id_attendancedetail")
    private AttendanceDetail id_attendancedetail;
}

enum StatusScore {
    Pass,
    Fail,

}
