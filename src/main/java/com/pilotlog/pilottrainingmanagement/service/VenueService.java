package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Venue;

import java.util.List;

public interface VenueService {

    Venue addVenue(Venue venue);
    List<Venue> getAllVenue();
    Venue getVenueById(String id);
    Venue updateVenue(Venue venue, String id);
    Venue deleteVenue(Venue venue, String id);

}
