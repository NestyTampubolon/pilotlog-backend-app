package com.pilotlog.pilottrainingmanagement.model;

import lombok.Getter;

@Getter
public enum Role {
    SUPERADMIN("Superadmin"),
    ADMIN("Admin"),
    TRAINEE("Trainee"),
    INSTRUCTOR("Instructor"),
    CPTS("CPTS"),
    TRAINEE_INSTRUCTOR("Trainee & Instructor"),
    TRAINEE_CPTS("Trainee & CPTS"),
    INSTRUCTOR_CPTS("Instructor & CPTS"),
    TRAINEE_INSTRUCTOR_CPTS("Trainee, Instructor & CPTS");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

}
