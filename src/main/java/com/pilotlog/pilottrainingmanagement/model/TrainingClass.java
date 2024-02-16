package com.pilotlog.pilottrainingmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "trainingClass")
public class TrainingClass {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_trainingclass;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_name", nullable = false)
    private String short_name;

    @Column(name = "recurrent", nullable = false)
    private String recurrent;

    @Column(name = "description")
    private String description;

    @Column(name = "is_delete", nullable = false)
    private byte is_delete;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "created_by", nullable = false)
    private String created_by;

    @Column(name = "updated_by", nullable = false)
    private String updated_by;

    @ManyToOne
    @JoinColumn(name="id_company")
    private Company id_company;

}


