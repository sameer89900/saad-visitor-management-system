package com.vms.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entry_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "visit_request_id")
    private VisitRequest visitRequest;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private LocalDateTime entryTime = LocalDateTime.now();

    private LocalDateTime exitTime;

    private String purpose;

    // NEW: floor/building destination selected at check-in (e.g. "Floor 3 — Finance").
    // Powers floor-level movement logging and the Reports traffic chart.
    private String location;

    @Column(unique = true)
    private String gatePassNumber;

    @ManyToOne
    @JoinColumn(name = "security_guard_id")
    private User securityGuard;

    private Double temperature;

    private String remarks;

    @Enumerated(EnumType.STRING)
    private Status status = Status.INSIDE;

    public enum Status {
        INSIDE, EXITED
    }

    public Long getDurationMinutes() {
        if (exitTime != null) {
            return java.time.Duration.between(entryTime, exitTime).toMinutes();
        }
        return null;
    }
}
