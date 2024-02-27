package com.pilotlog.pilottrainingmanagement.model;

public enum StatusUsers {
    VALID("VALID"),
    NOT_VALID("NOT VALID");

    private final String status;

    StatusUsers(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
