package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String> {
    @Query(value = "SELECT * FROM room WHERE id_company = :idCompany AND is_delete = 0", nativeQuery = true)
    List<Room> findAllByCompanyIdAndIsDeleteIsZero(@Param("idCompany") String idCompany);

}
