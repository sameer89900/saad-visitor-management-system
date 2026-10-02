package com.vms.controller;

import com.vms.repository.EntryLogRepository;
import com.vms.repository.VisitorRepository;
import com.vms.repository.VisitRequestRepository;
import com.vms.model.VisitRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    @Autowired
    private EntryLogRepository entryLogRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private VisitRequestRepository visitRequestRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();

        // Today's entries
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        long todayEntries = entryLogRepository.countEntriesBetween(startOfDay, endOfDay);

        // Currently inside
        long currentlyInside = entryLogRepository.countCurrentVisitors();

        // Total visitors
        long totalVisitors = visitorRepository.count();

        // Pending requests
        long pendingRequests = visitRequestRepository.countByStatus(VisitRequest.Status.PENDING);

        stats.put("todayEntries", todayEntries);
        stats.put("currentlyInside", currentlyInside);
        stats.put("totalVisitors", totalVisitors);
        stats.put("pendingRequests", pendingRequests);

        return ResponseEntity.ok(stats);
    }
}
