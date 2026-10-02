package com.vms.repository;

import com.vms.model.VisitRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRequestRepository extends JpaRepository<VisitRequest, Long> {

    List<VisitRequest> findByStatus(VisitRequest.Status status);

    List<VisitRequest> findByVisitorId(Long visitorId);

    List<VisitRequest> findByEmployeeId(Long employeeId);

    List<VisitRequest> findByVisitDate(LocalDate visitDate);

    Optional<VisitRequest> findByQrCode(String qrCode);

    long countByStatus(VisitRequest.Status status);

    @Query("SELECT vr FROM VisitRequest vr WHERE vr.visitDate = :today AND vr.status = 'APPROVED'")
    List<VisitRequest> findTodayApprovedRequests(LocalDate today);
}
