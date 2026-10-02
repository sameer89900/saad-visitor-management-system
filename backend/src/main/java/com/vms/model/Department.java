package com.vms.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dept_name", nullable = false, unique = true)
    private String deptName;

    private String building;

    @Column(name = "floor_number")
    private Integer floorNumber;

    private String description;

    @Column(name = "head_of_dept")
    private String headOfDept;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
