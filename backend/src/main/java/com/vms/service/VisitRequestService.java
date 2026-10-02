package com.vms.service;

import com.vms.model.VisitRequest;
import com.vms.repository.VisitRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisitRequestService {

    @Autowired
    private VisitRequestRepository visitRequestRepository;

    public List<VisitRequest> getAllRequests() {
        return visitRequestRepository.findAll();
    }

    public Optional<VisitRequest> getRequestById(Long id) {
        return visitRequestRepository.findById(id);
    }

    public VisitRequest createRequest(VisitRequest request) {
        // Auto-generate unique QR code on creation
        String qrCode = "VMS-" + UUID.randomUUID().toString()
                            .replace("-", "").substring(0, 8).toUpperCase();
        request.setQrCode(qrCode);
        request.setStatus(VisitRequest.Status.PENDING);
        return visitRequestRepository.save(request);
    }

    public VisitRequest approveRequest(Long id) {
        VisitRequest request = visitRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(VisitRequest.Status.APPROVED);
        request.setApprovedAt(java.time.LocalDateTime.now());
        return visitRequestRepository.save(request);
    }

    public VisitRequest rejectRequest(Long id, String reason) {
        VisitRequest request = visitRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(VisitRequest.Status.REJECTED);
        request.setRemarks(reason);
        return visitRequestRepository.save(request);
    }

    public Optional<VisitRequest> findByQrCode(String qrCode) {
        return visitRequestRepository.findByQrCode(qrCode);
    }

    public List<VisitRequest> getRequestsByStatus(VisitRequest.Status status) {
        return visitRequestRepository.findByStatus(status);
    }

    public List<VisitRequest> getTodayApprovedRequests() {
        return visitRequestRepository.findTodayApprovedRequests(LocalDate.now());
    }

    public List<VisitRequest> getRequestsByVisitor(Long visitorId) {
        return visitRequestRepository.findByVisitorId(visitorId);
    }

    public void deleteRequest(Long id) {
        visitRequestRepository.deleteById(id);
    }
}
