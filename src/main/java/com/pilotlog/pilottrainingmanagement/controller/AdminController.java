package com.pilotlog.pilottrainingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/public/messages")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class AdminController {

    @GetMapping
    public ResponseEntity<List<String>> messages(){
        System.out.println("Hi Admin");
        return ResponseEntity.ok(Arrays.asList("first", "second"));
    }
}
//    @GetMapping
//    public ResponseEntity<String> sayHello(){
//        System.out.println("Hi Admin");
//        return ResponseEntity.ok("Hi Admin");
//    }
