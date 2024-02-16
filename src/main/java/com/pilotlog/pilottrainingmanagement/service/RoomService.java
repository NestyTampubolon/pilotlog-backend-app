package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Department;
import com.pilotlog.pilottrainingmanagement.model.Room;

import java.util.List;

public interface RoomService {
    Room addRoom(Room room);
    List<Room> getAllRoom();
    Room getRoomById(String id);
    Room updateRoom(Room room, String id);
    Room deleteRoom(Room room, String id);
}
