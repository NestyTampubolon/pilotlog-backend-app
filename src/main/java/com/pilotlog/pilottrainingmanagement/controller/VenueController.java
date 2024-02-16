package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Venue;
import com.pilotlog.pilottrainingmanagement.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class VenueController {
    private final VenueService venueService;

    @PostMapping("addVenue")
    public ResponseEntity<Venue> addVenue(@RequestBody Venue venue){
        return new ResponseEntity<>(venueService.addVenue(venue), HttpStatus.CREATED);
    }

    @GetMapping("venue")
    public List<Venue> getAllVenue(){
        return venueService.getAllVenue();
    }

    @GetMapping("venue/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable("id") String idvenue){
        return new ResponseEntity<Venue>(venueService.getVenueById(idvenue), HttpStatus.OK);
    }

    @PutMapping("venue/update/{id}")
    public ResponseEntity<Venue> updateTraining(@PathVariable("id") String id,
                                                        @RequestBody Venue venue){
        return new ResponseEntity<Venue>(venueService.updateVenue(venue,id), HttpStatus.OK);
    }

    @PutMapping("venue/delete/{id}")
    public ResponseEntity<Venue> deleteVenue(@PathVariable("id") String id,
                                                        @RequestBody Venue venue){
        return new ResponseEntity<Venue>(venueService.deleteVenue(venue,id), HttpStatus.OK);
    }


}
