package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Venue;
import com.pilotlog.pilottrainingmanagement.repository.VenueRepository;
import com.pilotlog.pilottrainingmanagement.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;

    @Override
    public Venue addVenue(Venue venue) {
        Venue venueC = new Venue();
        venueC.setId_venue(venue.getId_venue());
        venueC.setName(venue.getName());
        venueC.set_delete(false);
        venueC.setId_company(AuthenticationServiceImpl.getCompanyInfo());
        venueC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        venueC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        venueC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        venueC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return venueRepository.save(venueC);
    }

    @Override
    public List<Venue> getAllVenue() {
        return venueRepository.findAllByCompanyIdAndIsDeleteIsZero(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

    @Override
    public Venue getVenueById(String id) {
        return venueRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Venue", "Id", id));
    }

    @Override
    public Venue updateVenue(Venue venue, String id) {
        Venue existingVenue = venueRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Venue", "Id", id)
        );

        existingVenue.setName(venue.getName());
        existingVenue.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingVenue.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        venueRepository.save(existingVenue);

        return existingVenue;
    }

    @Override
    public Venue deleteVenue(Venue venue, String id) {

        Venue existingVenue = venueRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Venue", "Id", id)
        );

        existingVenue.set_delete(true);
        venueRepository.save(existingVenue);
        return existingVenue;
    }
}
