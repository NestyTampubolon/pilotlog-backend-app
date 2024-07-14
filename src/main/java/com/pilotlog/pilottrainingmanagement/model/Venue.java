package com.pilotlog.pilottrainingmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_venue;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_delete", nullable = false)
    private boolean is_delete;

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
