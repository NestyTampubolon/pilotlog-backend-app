package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // menambah data room yang dilakukan oleh admin
    @PostMapping("addRoom")
    public ResponseEntity<Room> addRoom(@RequestBody Room room){
        return new ResponseEntity<>(roomService.addRoom(room), HttpStatus.CREATED);
    }

    // mendapatkan data semua room
    @GetMapping("room")
    public List<Room> getAllRoom(){
        return roomService.getAllRoom();
    }

    // mendaptkan data room berdasarkan id
    @GetMapping("room/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable("id") String idroom){
        return new ResponseEntity<Room>(roomService.getRoomById(idroom), HttpStatus.OK);
    }

    // mengubah data room berdasarkan id
    @PutMapping("room/update/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable("id") String id,
                                                @RequestBody Room room){
        return new ResponseEntity<Room>(roomService.updateRoom(room,id), HttpStatus.OK);
    }


    // menghapus data room berdasarkan id
    @PutMapping("room/delete/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable("id") String id,
                                             @RequestBody Room venue){
        return new ResponseEntity<Room>(roomService.deleteRoom(venue,id), HttpStatus.OK);
    }

}
