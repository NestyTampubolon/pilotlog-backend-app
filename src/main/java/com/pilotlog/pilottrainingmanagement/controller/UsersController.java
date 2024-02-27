package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.service.CompanyService;
import com.pilotlog.pilottrainingmanagement.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    // build create Users
    @PostMapping("admin/addUsers")
    public ResponseEntity<Users> addUsers(@RequestBody Users users){
        return new ResponseEntity<>(usersService.addUser(users), HttpStatus.CREATED);
    }

    // get all users
    @GetMapping("admin/users")
    public List<Users> getAllUsers(){
        return usersService.getAllUsers();
    }

    @GetMapping("admin/instructor")
    public List<Users> getAllInstructor(){
        return usersService.getAllInstructor();
    }

    //build get employee by id
    @GetMapping("admin/users/{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable("id") String idusers){
        return new ResponseEntity<Users>(usersService.getUsersById(idusers), HttpStatus.OK);
    }

    //build update Users
    @PutMapping("admin/users/{id}")
    public ResponseEntity<Users> updateUsers(@PathVariable("id") String id,
                                             @RequestBody Users users){
        return new ResponseEntity<Users>(usersService.editUsers(users,id), HttpStatus.OK);
    }

    //build delete Users
    @DeleteMapping("admin/users/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable("id") String id){
        usersService.deleteUsers(id);
        return  new ResponseEntity<>("Users delete successfully", HttpStatus.OK);

    }

    @PutMapping("admin/activationusers/{id}")
    public ResponseEntity<Users> activationUsers(@PathVariable("id") String id,
                                             @RequestBody Users users){
        return new ResponseEntity<Users>(usersService.activationUsers(users,id), HttpStatus.OK);
    }

//    @GetMapping("admin/user")
//    public ResponseEntity<String> getUserInfo() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Users user = (Users) authentication.getPrincipal();
//        String userId = user.getId_users();
//        return ResponseEntity.ok(userId);
//    }
}
