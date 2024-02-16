package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Department;
import com.pilotlog.pilottrainingmanagement.model.Venue;

import java.util.List;

public interface DepartmentService {
    Department addDepartment(Department department);
    List<Department> getAllDepartment();
    Department getDepartmentById(String id);
    Department updateDepartment(Department department, String id);
    Department deleteDeparment(Department department, String id);
}
