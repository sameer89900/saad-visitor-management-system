package com.vms.service;

import com.vms.model.Employee;
import com.vms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmpCode(employee.getEmpCode())) {
            throw new RuntimeException("Employee code already exists: " + employee.getEmpCode());
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee details) {
        Employee emp = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setFirstName(details.getFirstName());
        emp.setLastName(details.getLastName());
        emp.setDesignation(details.getDesignation());
        emp.setPhone(details.getPhone());
        emp.setEmail(details.getEmail());
        emp.setDepartment(details.getDepartment());
        emp.setActive(details.getActive());
        return employeeRepository.save(emp);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }
}
