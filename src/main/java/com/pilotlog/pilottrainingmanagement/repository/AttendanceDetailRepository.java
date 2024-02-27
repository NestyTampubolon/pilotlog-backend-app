package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceDetailRepository extends JpaRepository<Attendance, String> {
}
