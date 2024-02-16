package com.pilotlog.pilottrainingmanagement.controller;

import com.pilotlog.pilottrainingmanagement.model.Department;
import com.pilotlog.pilottrainingmanagement.model.Venue;
import com.pilotlog.pilottrainingmanagement.service.DepartmentService;
import com.pilotlog.pilottrainingmanagement.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("addDepartment")
    public ResponseEntity<Department> addVenue(@RequestBody Department department){
        return new ResponseEntity<>(departmentService.addDepartment(department), HttpStatus.CREATED);
    }

    @GetMapping("department")
    public List<Department> getAllDepartment(){
        return departmentService.getAllDepartment();
    }

    @GetMapping("department/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") String iddepartment){
        return new ResponseEntity<Department>(departmentService.getDepartmentById(iddepartment), HttpStatus.OK);
    }

    @PutMapping("department/update/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("id") String id,
                                                @RequestBody Department department){
        return new ResponseEntity<Department>(departmentService.updateDepartment(department,id), HttpStatus.OK);
    }

    @PutMapping("department/delete/{id}")
    public ResponseEntity<Department> deleteVenue(@PathVariable("id") String id,
                                             @RequestBody Department department){
        return new ResponseEntity<Department>(departmentService.deleteDeparment(department,id), HttpStatus.OK);
    }
}
