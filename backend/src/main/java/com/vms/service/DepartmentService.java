package com.vms.service;

import com.vms.model.Department;
import com.vms.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department details) {
        Department dept = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found"));
        dept.setDeptName(details.getDeptName());
        dept.setBuilding(details.getBuilding());
        dept.setFloorNumber(details.getFloorNumber());
        dept.setDescription(details.getDescription());
        dept.setHeadOfDept(details.getHeadOfDept());
        return departmentRepository.save(dept);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
