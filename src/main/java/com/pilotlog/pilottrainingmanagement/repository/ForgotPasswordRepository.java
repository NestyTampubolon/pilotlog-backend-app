package com.pilotlog.pilottrainingmanagement.repository;

import com.pilotlog.pilottrainingmanagement.model.ForgotPassword;
import com.pilotlog.pilottrainingmanagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
    @Query("Select fp from ForgotPassword fp WHERE fp.otp = ?1 and fp.users = ?2")
    Optional<ForgotPassword> findByOtpAndUsers(Integer otp, Users users);
}
