package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.dto.JwtAuthenticationResponse;
import com.pilotlog.pilottrainingmanagement.dto.RefreshTokenRequest;
import com.pilotlog.pilottrainingmanagement.dto.SignUpRequest;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager manager;

    // melakukan registrasi akun
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest users){
        return ResponseEntity.ok(authenticationService.signUp(users));
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public  ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody Users users){
        return ResponseEntity.ok(authenticationService.signIn(users));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @GetMapping("/users/profil")
    public ResponseEntity<Users> getUserProfil(){
        Users userProfile = authenticationService.getUserProfile();
        return ResponseEntity.ok(userProfile);
    }


}
