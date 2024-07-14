package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Department;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.model.Venue;
import com.pilotlog.pilottrainingmanagement.repository.DepartmentRepository;
import com.pilotlog.pilottrainingmanagement.repository.VenueRepository;
import com.pilotlog.pilottrainingmanagement.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public Department addDepartment(Department department) {
        Department departmentC = new Department();
        departmentC.setId_department(department.getId_department());
        departmentC.setName(department.getName());
        departmentC.set_delete(false);
        departmentC.setId_company(AuthenticationServiceImpl.getCompanyInfo());
        departmentC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        departmentC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        departmentC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        departmentC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return departmentRepository.save(departmentC);
    }

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepository.findAllByCompanyIdAndIsDeleteIsZero(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

//    @Override
//    public List<Department> getAllDepartment() {
//        return departmentRepository.findAll();
//    }

    @Override
    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", id));
    }

    @Override
    public Department updateDepartment(Department department, String id) {
        Department existingDepartment = departmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department", "Id", id)
        );

        existingDepartment.setName(department.getName());
        existingDepartment.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingDepartment.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        departmentRepository.save(existingDepartment);

        return existingDepartment;
    }

    @Override
    public Department deleteDeparment(Department department, String id) {
        Department existingDepartment = departmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department", "Id", id)
        );

        existingDepartment.set_delete(true);
        departmentRepository.save(existingDepartment);
        return existingDepartment;
    }
}
