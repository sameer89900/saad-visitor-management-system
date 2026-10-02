package com.vms.repository;

import com.vms.model.EntryLog;
import com.vms.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EntryLogRepository extends JpaRepository<EntryLog, Long> {

    List<EntryLog> findByVisitor(Visitor visitor);

    List<EntryLog> findByVisitorId(Long visitorId);

    // Currently inside — no exit time recorded
    @Query("SELECT e FROM EntryLog e WHERE e.exitTime IS NULL AND e.status = 'INSIDE'")
    List<EntryLog> findAllCurrentlyInside();

    @Query("SELECT COUNT(e) FROM EntryLog e WHERE e.exitTime IS NULL AND e.status = 'INSIDE'")
    long countCurrentlyInside();

    // Today's entries count
    @Query("SELECT COUNT(e) FROM EntryLog e WHERE e.entryTime >= :start AND e.entryTime < :end")
    long countEntriesBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(e) FROM EntryLog e WHERE e.entryTime >= :startOfDay")
    long countTodayEntries(LocalDateTime startOfDay);

    // For dashboard stats
    @Query("SELECT COUNT(e) FROM EntryLog e WHERE e.exitTime IS NULL")
    long countCurrentVisitors();

    Optional<EntryLog> findTopByVisitorIdOrderByEntryTimeDesc(Long visitorId);

    List<EntryLog> findByEntryTimeBetween(LocalDateTime start, LocalDateTime end);

    // Default method used by DashboardController
    default long countTodayEntries() {
        LocalDateTime startOfDay = java.time.LocalDate.now().atStartOfDay();
        return countTodayEntries(startOfDay);
    }
}
