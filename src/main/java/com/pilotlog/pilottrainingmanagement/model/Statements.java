package com.pilotlog.pilottrainingmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "statements")
public class Statements {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_statements;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_active", nullable = false)
    private boolean is_active;

    @Column(name = "is_delete", nullable = false)
    private boolean is_delete;

    @Column(name = "statementType", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatementType statementType;

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
