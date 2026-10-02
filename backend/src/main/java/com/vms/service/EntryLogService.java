package com.vms.service;

import com.vms.dto.CheckInRequest;
import com.vms.model.EntryLog;
import com.vms.model.Visitor;
import com.vms.repository.EntryLogRepository;
import com.vms.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntryLogService {

    @Autowired
    private EntryLogRepository entryLogRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    public List<EntryLog> getAllLogs() {
        return entryLogRepository.findAll();
    }

    public Optional<EntryLog> getLogById(Long id) {
        return entryLogRepository.findById(id);
    }

    // Accepts the flat { visitorId, purpose, location, temperature } shape sent by the frontend
    public EntryLog checkIn(CheckInRequest request) {
        Visitor visitor = visitorRepository.findById(request.getVisitorId())
                .orElseThrow(() -> new RuntimeException("Visitor not found: " + request.getVisitorId()));

        EntryLog entryLog = new EntryLog();
        entryLog.setVisitor(visitor);
        entryLog.setPurpose(request.getPurpose());
        entryLog.setLocation(request.getLocation());
        entryLog.setTemperature(request.getTemperature());
        entryLog.setGatePassNumber("GP-" + System.currentTimeMillis());
        entryLog.setStatus(EntryLog.Status.INSIDE);
        entryLog.setEntryTime(LocalDateTime.now());

        return entryLogRepository.save(entryLog);
    }

    public EntryLog checkOut(Long logId) {
        EntryLog log = entryLogRepository.findById(logId)
            .orElseThrow(() -> new RuntimeException("Entry log not found"));

        log.setExitTime(LocalDateTime.now());
        log.setStatus(EntryLog.Status.EXITED);

        return entryLogRepository.save(log);
    }

    public List<EntryLog> getCurrentlyInside() {
        return entryLogRepository.findAllCurrentlyInside();
    }

    public long getInsideCount() {
        return entryLogRepository.countCurrentlyInside();
    }

    public long getTodayCount() {
        return entryLogRepository.countTodayEntries();
    }

    public List<EntryLog> getVisitorHistory(Visitor visitor) {
        return entryLogRepository.findByVisitor(visitor);
    }
}
