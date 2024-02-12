package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.dto.JwtAuthenticationResponse;
import com.pilotlog.pilottrainingmanagement.dto.RefreshTokenRequest;
import com.pilotlog.pilottrainingmanagement.model.Users;

public interface AuthenticationService {
    Users signUp(Users users);
    JwtAuthenticationResponse signIn(Users users);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
