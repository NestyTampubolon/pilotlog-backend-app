package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.service.CompanyService;
import com.pilotlog.pilottrainingmanagement.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
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

    @GetMapping("cpts/users")
    public List<Users> getAllPilot(){
        return usersService.getAllPilot();
    }

    @GetMapping("admin/cpts")
    public List<Users> getAllCPTS(){
        return usersService.getAllCPTS();
    }

    //build get employee by id
    @GetMapping("public/users/{id}")
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

    @PutMapping("public/changepassword/{id}")
    public ResponseEntity<Users> changePassword(@PathVariable("id") String id, @RequestBody Users users){
        return new ResponseEntity<Users>(usersService.changePassword(users, id), HttpStatus.OK);
    }

    @PutMapping(value = "public/users/update/profile/{id}")
    public ResponseEntity<Users> updateProfile(@PathVariable("id") String id,
                                              @RequestBody MultipartFile profile) {
        return new ResponseEntity<>(usersService.updatePhotoProfile(profile, id), HttpStatus.OK);
    }

    @Value("${profile.directory}")
    private String profileDirectory;

    @GetMapping("images/profile/{imageName}")
    public ResponseEntity<Resource> getImageProfile(@PathVariable String imageName) throws MalformedURLException {
        Path imagePath = Paths.get(profileDirectory).resolve(imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageResource);
    }

    @GetMapping("cpts/countpilot")
    public ResponseEntity<Map<String, BigInteger>> findAllPilotsCountsByCompanyId(){
        Map<String, BigInteger> pilotsCounts = usersService.findAllPilotsCountsByCompanyId();
        System.out.println("check");
        return ResponseEntity.ok(pilotsCounts);
    }

}
