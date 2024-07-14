package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.dto.JwtAuthenticationResponse;
import com.pilotlog.pilottrainingmanagement.dto.RefreshTokenRequest;
import com.pilotlog.pilottrainingmanagement.dto.SignUpRequest;
import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.AttendanceDetail;
import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.model.Role;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.repository.CompanyRepository;
import com.pilotlog.pilottrainingmanagement.repository.UsersRepository;
import com.pilotlog.pilottrainingmanagement.service.AttendanceDetailService;
import com.pilotlog.pilottrainingmanagement.service.AuthenticationService;
import com.pilotlog.pilottrainingmanagement.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private final CompanyRepository companyRepository;

    @Autowired
    private  final UsersRepository usersRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JWTService jwtService;

    private  final AttendanceDetailService attendanceDetailService;
    protected String generateUniqueCompanyId() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt;
        String saltStr = null;
        boolean isUnique = false;

        // Loop until a unique ID is generated
        while (!isUnique) {
            salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 6) {
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            saltStr = salt.toString();

            // Check if the generated ID already exists in the database
            Optional<Company> existingCompany = companyRepository.findById(saltStr);
            if (!existingCompany.isPresent()) {
                isUnique = true;
            }
        }

        return saltStr;
    }

    @Override
    public ResponseEntity<?> signUp(SignUpRequest signUpRequest) {
        try {
            String companyId = generateUniqueCompanyId();

            Company companyC = new Company();
            companyC.setId_company(companyId);
            companyC.setName(signUpRequest.getCompany().getName());
            companyC.setEmail(signUpRequest.getCompany().getEmail());
            companyC.setLogo(signUpRequest.getCompany().getLogo());
            companyC.setContact(signUpRequest.getCompany().getContact());
            companyC.set_active(false);
            companyC.set_delete(false);
            companyC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
            companyC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
            companyC.setCreated_by(companyId + signUpRequest.getUsers().getId_no());
            companyC.setUpdated_by(companyId + signUpRequest.getUsers().getId_no());
            companyRepository.save(companyC);

            Company existingCompany = companyRepository.findById(String.valueOf(companyId)).orElseThrow(
                    () -> new ResourceNotFoundException("Company Id", "Id", companyId)
            );


            Users usersc = new Users();
            usersc.setId_users(companyId + signUpRequest.getUsers().getId_no());
            usersc.setId_no(signUpRequest.getUsers().getId_no());
            usersc.setName(signUpRequest.getUsers().getName());
            usersc.setEmail(signUpRequest.getUsers().getEmail());
            usersc.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getUsers().getPassword()));
            usersc.setHub(signUpRequest.getUsers().getHub());
            usersc.setRole(Role.ADMIN);
            usersc.set_active(true);
            usersc.setId_company(existingCompany);
            usersc.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
            usersc.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
            usersc.setCreated_by(companyId + signUpRequest.getUsers().getId_no());
            usersc.setUpdated_by(companyId + signUpRequest.getUsers().getId_no());
            usersRepository.save(usersc);
        } catch (Exception ex) {
            // Handle the exception here (e.g., logging, returning an error response)
            ex.printStackTrace(); // You can replace this with your error handling logic
            return new ResponseEntity<>("An error occurred during sign up.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Sign up successful.", HttpStatus.OK);
    }


    public JwtAuthenticationResponse signIn(Users signinRequest){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword());

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
        jwtAuthenticationResponse.setUsers(user);
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

    public static Users getUserProfileInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = (Users) authentication.getPrincipal();
        return user;
    }

    public Users getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = (Users) authentication.getPrincipal();
        return user;
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
