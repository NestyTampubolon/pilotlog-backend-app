package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.dto.JwtAuthenticationResponse;
import com.pilotlog.pilottrainingmanagement.dto.RefreshTokenRequest;
import com.pilotlog.pilottrainingmanagement.dto.SignUpRequest;
import com.pilotlog.pilottrainingmanagement.model.Users;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(Users users);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    Users getUserProfile();

}
