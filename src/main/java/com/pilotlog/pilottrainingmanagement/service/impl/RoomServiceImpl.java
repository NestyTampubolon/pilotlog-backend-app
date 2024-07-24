package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.repository.RoomRepository;
import com.pilotlog.pilottrainingmanagement.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    // menambah data room
    @Override
    public Room addRoom(Room room) {
        Room roomC = new Room();
        roomC.setId_room(room.getId_room());
        roomC.setName(room.getName());
        roomC.set_delete(false);
        roomC.setId_company(AuthenticationServiceImpl.getCompanyInfo());
        roomC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        roomC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        roomC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        roomC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return roomRepository.save(roomC);
    }

    // mendapatkan data semua room
    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAllByCompanyIdAndIsDeleteIsZero(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

    // mendapatkan data room berdasarkan id
    @Override
    public Room getRoomById(String id) {
        return roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", id));
    }

    // mengubah data room
    @Override
    public Room updateRoom(Room room, String id) {
        Room existingRoom = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room", "Id", id)
        );

        existingRoom.setName(room.getName());
        existingRoom.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingRoom.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        roomRepository.save(existingRoom);

        return existingRoom;
    }

    // menghapus data room
    @Override
    public Room deleteRoom(Room room, String id) {
        Room existingRoom = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room", "Id", id)
        );

        existingRoom.set_delete(true);
        roomRepository.save(existingRoom);
        return existingRoom;
    }
}
