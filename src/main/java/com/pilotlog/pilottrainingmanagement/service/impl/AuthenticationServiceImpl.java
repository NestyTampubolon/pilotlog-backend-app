package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.dto.JwtAuthenticationResponse;
import com.pilotlog.pilottrainingmanagement.dto.RefreshTokenRequest;
import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.repository.UsersRepository;
import com.pilotlog.pilottrainingmanagement.service.AuthenticationService;
import com.pilotlog.pilottrainingmanagement.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private  final UsersRepository usersRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JWTService jwtService;

    @Autowired
    public AuthenticationServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Users signUp(Users signUpRequest) {
        Users users = new Users();
        users.setId_users(signUpRequest.getId_users());
        users.setId_no(signUpRequest.getId_no());
        users.setName(signUpRequest.getName());
        users.setEmail(signUpRequest.getEmail());
        users.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        users.setRank(signUpRequest.getRank());
        users.setHub(signUpRequest.getHub());
        users.setLicense_no(signUpRequest.getLicense_no());
        users.setPhoto_profile(signUpRequest.getPhoto_profile());
        users.setRole(signUpRequest.getRole());
        users.setIs_active(signUpRequest.getIs_active());
        users.setCreated_at(signUpRequest.getCreated_at());
        users.setUpdated_at(signUpRequest.getUpdated_at());
        users.setCreated_by(signUpRequest.getCreated_by());
        users.setUpdated_by(signUpRequest.getUpdated_by());
        return usersRepository.save(users);
    }

    public JwtAuthenticationResponse signIn(Users signinRequest){
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword());

        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid username or password");
            e.printStackTrace();
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        } catch (Exception e) {
            System.out.println("Unexpected error occurred during authentication");
            e.printStackTrace();
            throw new RuntimeException("Unexpected error occurred during authentication", e);
        }

        var user = usersRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        Users users = usersRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), users)){
            var jwt = jwtService.generateToken(users);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;

    }


    public static String getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = (Users) authentication.getPrincipal();
        String userId = user.getId_users();
        return userId;
    }


    public static Company getCompanyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Users) {
            Users user = (Users) authentication.getPrincipal();
            if (user.getId_company() != null) {
                return user.getId_company();
            }
        }
        return null;
    }

}
