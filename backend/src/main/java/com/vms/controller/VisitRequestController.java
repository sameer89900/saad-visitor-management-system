package com.vms.controller;

import com.vms.model.VisitRequest;
import com.vms.service.VisitRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visit-requests")
@CrossOrigin(origins = "http://localhost:3000")
public class VisitRequestController {

    @Autowired
    private VisitRequestService visitRequestService;

    @GetMapping
    public ResponseEntity<List<VisitRequest>> getAllRequests() {
        return ResponseEntity.ok(visitRequestService.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitRequest> getRequestById(@PathVariable Long id) {
        return visitRequestService.getRequestById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VisitRequest> createRequest(@RequestBody VisitRequest request) {
        return ResponseEntity.ok(visitRequestService.createRequest(request));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<VisitRequest> approveRequest(@PathVariable Long id) {
        return ResponseEntity.ok(visitRequestService.approveRequest(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<VisitRequest> rejectRequest(@PathVariable Long id,
                                                       @RequestBody Map<String, String> body) {
        String reason = body.getOrDefault("reason", "Rejected by admin");
        return ResponseEntity.ok(visitRequestService.rejectRequest(id, reason));
    }

    @GetMapping("/qr/{qrCode}")
    public ResponseEntity<VisitRequest> findByQrCode(@PathVariable String qrCode) {
        return visitRequestService.findByQrCode(qrCode)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/today-approved")
    public ResponseEntity<List<VisitRequest>> getTodayApproved() {
        return ResponseEntity.ok(visitRequestService.getTodayApprovedRequests());
    }

    @GetMapping("/visitor/{visitorId}")
    public ResponseEntity<List<VisitRequest>> getByVisitor(@PathVariable Long visitorId) {
        return ResponseEntity.ok(visitRequestService.getRequestsByVisitor(visitorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        visitRequestService.deleteRequest(id);
        return ResponseEntity.ok().build();
    }
}
