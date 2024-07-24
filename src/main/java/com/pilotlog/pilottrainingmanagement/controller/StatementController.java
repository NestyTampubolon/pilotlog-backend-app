package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Statements;
import com.pilotlog.pilottrainingmanagement.service.StatementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class StatementController {

    private final StatementsService statementsService;

    // menambah data statement
    @PostMapping("admin/addStatements")
    public ResponseEntity<Statements> addStatements(@RequestBody Statements statements){
        return new ResponseEntity<>(statementsService.addStatements(statements), HttpStatus.CREATED);
    }

    // mendapatkan semua data statement
    @GetMapping("admin/statements")
    public List<Statements> getAllStatements(){
        return statementsService.getAllStatments();
    }

    // mendapatkan semua data statement yang dapat dilihat oleh trainee
    @GetMapping("trainee/statements")
    public List<Statements> getAllStatementsForInstructor(){
        return statementsService.getAllStatmentsForInstructor();
    }

    // mendapatkan semua data statement yang dapat dilihat oleh instructor
    @GetMapping("instructor/statements")
    public List<Statements> getAllStatementsForTrainee(){
        return statementsService.getAllStatmentsForTrainee();
    }

    // mengubah data statement
    @PutMapping("admin/statements/update/{id}")
    public ResponseEntity<Statements> updateStatements(@PathVariable("id") String id,
                                               @RequestBody Statements statements){
        return new ResponseEntity<Statements>(statementsService.updateStatements(statements, Long.valueOf(id)), HttpStatus.OK);
    }

    // melakukan aktivasi statement yang dilakukan oleh admin
    @PutMapping("admin/statements/activation/{id}")
    public ResponseEntity<Statements> activationStatements(@PathVariable("id") String id,
                                                       @RequestBody Statements statements){
        return new ResponseEntity<Statements>(statementsService.activationStatements(statements, Long.valueOf(id)), HttpStatus.OK);
    }

    // menghapus data statement berdasarkan id
    @PutMapping("admin/statements/delete/{id}")
    public ResponseEntity<Statements> deleteStatements(@PathVariable("id") String id,
                                            @RequestBody Statements statements){
        return new ResponseEntity<Statements>(statementsService.deleteStatement(statements, Long.valueOf(id)), HttpStatus.OK);
    }
}
