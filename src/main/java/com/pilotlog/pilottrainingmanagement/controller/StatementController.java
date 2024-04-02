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

    @PostMapping("admin/addStatements")
    public ResponseEntity<Statements> addStatements(@RequestBody Statements statements){
        return new ResponseEntity<>(statementsService.addStatements(statements), HttpStatus.CREATED);
    }

    @GetMapping("admin/statements")
    public List<Statements> getAllStatements(){
        return statementsService.getAllStatments();
    }

    @GetMapping("trainee/statements")
    public List<Statements> getAllStatementsForInstructor(){
        return statementsService.getAllStatmentsForInstructor();
    }

    @GetMapping("instructor/statements")
    public List<Statements> getAllStatementsForTrainee(){
        return statementsService.getAllStatmentsForTrainee();
    }
    @PutMapping("admin/statements/update/{id}")
    public ResponseEntity<Statements> updateStatements(@PathVariable("id") String id,
                                               @RequestBody Statements statements){
        return new ResponseEntity<Statements>(statementsService.updateStatements(statements, Long.valueOf(id)), HttpStatus.OK);
    }

    @PutMapping("admin/statements/activation/{id}")
    public ResponseEntity<Statements> activationStatements(@PathVariable("id") String id,
                                                       @RequestBody Statements statements){
        return new ResponseEntity<Statements>(statementsService.activationStatements(statements, Long.valueOf(id)), HttpStatus.OK);
    }

    @PutMapping("admin/statements/delete/{id}")
    public ResponseEntity<Statements> deleteStatements(@PathVariable("id") String id,
                                            @RequestBody Statements statements){
        return new ResponseEntity<Statements>(statementsService.deleteStatement(statements, Long.valueOf(id)), HttpStatus.OK);
    }
}
