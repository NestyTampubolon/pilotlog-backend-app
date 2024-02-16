package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingClassRepository extends JpaRepository<TrainingClass, String> {
}
