package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Users;
import org.apache.catalina.User;

import java.util.List;

public interface UsersService {
    Users addUser(Users users);
    List<Users> getAllUsers();
    Users getUsersById(String id);
    Users editUsers(Users users, String id);
    void deleteUsers(String id);

}
