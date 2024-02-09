package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        super();
        this.usersService = usersService;
    }

    // build create Users
    @PostMapping("addUsers")
    public ResponseEntity<Users> addUsers(@RequestBody Users users){
        return new ResponseEntity<>(usersService.addUser(users), HttpStatus.CREATED);
    }

    // get all users
    @GetMapping
    public List<Users> getAllUsers(){
        return usersService.getAllUsers();
    }

    //build get employee by id
    @GetMapping("{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable("id") String idusers){
        return new ResponseEntity<Users>(usersService.getUsersById(idusers), HttpStatus.OK);
    }

    //build update Users
    @PutMapping("{id}")
    public ResponseEntity<Users> updateUsers(@PathVariable("id") String id,
                                             @RequestBody Users users){
        return new ResponseEntity<Users>(usersService.editUsers(users,id), HttpStatus.OK);
    }

    //build delete Users
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable("id") String id){
        usersService.deleteUsers(id);

        return  new ResponseEntity<>("Users delete successfully", HttpStatus.OK);


    }
}
