package com.pilotlog.pilottrainingmanagement.dto;

import com.pilotlog.pilottrainingmanagement.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;

    private Users users;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Users getUsers() { return  users;}

    public void setUsers(Users users) {
        this.users = users;
    }
}
