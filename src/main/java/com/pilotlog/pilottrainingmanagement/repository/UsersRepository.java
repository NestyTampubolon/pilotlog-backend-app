package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.Role;
import com.pilotlog.pilottrainingmanagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByEmail(String email);
    Users findByRole(Role role);


}
