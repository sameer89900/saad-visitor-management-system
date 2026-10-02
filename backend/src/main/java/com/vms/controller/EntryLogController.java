package com.vms.controller;

import com.vms.dto.CheckInRequest;
import com.vms.model.EntryLog;
import com.vms.service.EntryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/entry-logs")
@CrossOrigin(origins = "http://localhost:3000")
public class EntryLogController {

    @Autowired
    private EntryLogService entryLogService;

    @GetMapping
    public ResponseEntity<List<EntryLog>> getAllLogs() {
        return ResponseEntity.ok(entryLogService.getAllLogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntryLog> getLogById(@PathVariable Long id) {
        return entryLogService.getLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CHANGED: now accepts the flat { visitorId, purpose, location, temperature } shape
    // sent by the frontend, instead of a full nested EntryLog object.
    @PostMapping("/checkin")
    public ResponseEntity<EntryLog> checkIn(@RequestBody CheckInRequest request) {
        return ResponseEntity.ok(entryLogService.checkIn(request));
    }

    @PutMapping("/checkout/{id}")
    public ResponseEntity<EntryLog> checkOut(@PathVariable Long id) {
        return ResponseEntity.ok(entryLogService.checkOut(id));
    }

    @GetMapping("/current")
    public ResponseEntity<List<EntryLog>> getCurrentVisitors() {
        return ResponseEntity.ok(entryLogService.getCurrentlyInside());
    }

    @GetMapping("/visitor/{visitorId}")
    public ResponseEntity<List<EntryLog>> getLogsByVisitor(@PathVariable Long visitorId) {
        // Delegates to service; requires a Visitor lookup if you want to filter by ID directly.
        // Kept simple here — filter the full list by visitor id since VisitorRepository
        // already loads the entity in checkIn().
        List<EntryLog> all = entryLogService.getAllLogs();
        all.removeIf(log -> log.getVisitor() == null || !log.getVisitor().getId().equals(visitorId));
        return ResponseEntity.ok(all);
    }

    @GetMapping("/stats/today")
    public ResponseEntity<Long> getTodayCount() {
        return ResponseEntity.ok(entryLogService.getTodayCount());
    }

    @GetMapping("/stats/current")
    public ResponseEntity<Long> getCurrentCount() {
        return ResponseEntity.ok(entryLogService.getInsideCount());
    }
}
