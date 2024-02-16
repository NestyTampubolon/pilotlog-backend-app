package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VenueRepository extends JpaRepository<Venue, String> {
}
