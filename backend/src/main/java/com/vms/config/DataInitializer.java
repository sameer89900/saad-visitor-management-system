package com.vms.config;

import com.vms.model.Department;
import com.vms.model.Employee;
import com.vms.model.User;
import com.vms.repository.DepartmentRepository;
import com.vms.repository.EmployeeRepository;
import com.vms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // ── Create default users if they don't exist ──────────────────
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@vms.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);
            System.out.println("✓ Admin user created — username: admin / password: admin123");
        }

        if (!userRepository.existsByUsername("security")) {
            User guard = new User();
            guard.setUsername("security");
            guard.setEmail("security@vms.com");
            guard.setPassword(passwordEncoder.encode("security123"));
            guard.setRole(User.Role.SECURITY);
            guard.setActive(true);
            userRepository.save(guard);
            System.out.println("✓ Security user created — username: security / password: security123");
        }

        // ── Create default departments if none exist ───────────────────
        if (departmentRepository.count() == 0) {
            String[][] depts = {
                {"IT Department",         "Building A", "1"},
                {"HR Department",         "Building A", "2"},
                {"Finance Department",    "Building B", "3"},
                {"Management",            "Building B", "4"},
                {"Operations",            "Building C", "1"},
            };
            for (String[] d : depts) {
                Department dept = new Department();
                dept.setDeptName(d[0]);
                dept.setBuilding(d[1]);
                dept.setFloorNumber(Integer.parseInt(d[2]));
                departmentRepository.save(dept);
            }
            System.out.println("✓ Default departments created");
        }

        // ── Create sample employees if none exist ──────────────────────
        if (employeeRepository.count() == 0) {
            Department itDept = departmentRepository.findByDeptName("IT Department").orElse(null);
            Department hrDept = departmentRepository.findByDeptName("HR Department").orElse(null);

            if (itDept != null) {
                Employee e1 = new Employee();
                e1.setEmpCode("EMP001");
                e1.setFirstName("Rajesh");
                e1.setLastName("Kumar");
                e1.setDesignation("Software Engineer");
                e1.setPhone("9876500001");
                e1.setEmail("rajesh@company.com");
                e1.setDepartment(itDept);
                employeeRepository.save(e1);
            }
            if (hrDept != null) {
                Employee e2 = new Employee();
                e2.setEmpCode("EMP002");
                e2.setFirstName("Priya");
                e2.setLastName("Sharma");
                e2.setDesignation("HR Manager");
                e2.setPhone("9876500002");
                e2.setEmail("priya@company.com");
                e2.setDepartment(hrDept);
                employeeRepository.save(e2);
            }
            System.out.println("✓ Sample employees created");
        }

        System.out.println("====================================");
        System.out.println("VMS Backend Ready!");
        System.out.println("API: http://localhost:8080/api");
        System.out.println("Login: admin / admin123");
        System.out.println("====================================");
    }
}
