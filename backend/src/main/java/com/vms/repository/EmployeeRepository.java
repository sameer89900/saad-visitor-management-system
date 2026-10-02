package com.vms.repository;

import com.vms.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmpCode(String empCode);

    List<Employee> findByDepartmentId(Long departmentId);

    List<Employee> findByActive(Boolean active);

    boolean existsByEmpCode(String empCode);
}
