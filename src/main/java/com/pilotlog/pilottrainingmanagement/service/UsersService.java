package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Users;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public interface UsersService {
    Users addUser(Users users);
    List<Users> getAllUsers();
    List<Users> getAllInstructor();
    List<Users> getAllCPTS();
    List<Users> getAllPilot();
    Users getUsersById(String id);
    Users editUsers(Users users, String id);
    void deleteUsers(String id);
    Users activationUsers(Users users, String id);
    UserDetailsService userDetailsService();
    Users changePassword(Users users, String id);
    Users updatePhotoProfile(MultipartFile profle, String id);
    ResponseEntity<byte[]> loadProfile(String filename) throws MalformedURLException;
    Map<String, BigInteger> findAllPilotsCountsByCompanyId();

}
