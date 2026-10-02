package com.vms.controller;

import com.vms.model.Blacklist;
import com.vms.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blacklist")
@CrossOrigin(origins = "http://localhost:3000")
public class BlacklistController {

    @Autowired
    private BlacklistService blacklistService;

    @GetMapping
    public ResponseEntity<List<Blacklist>> getAllBlacklisted() {
        return ResponseEntity.ok(blacklistService.getAllBlacklisted());
    }

    @PostMapping("/{visitorId}")
    public ResponseEntity<Blacklist> addToBlacklist(
            @PathVariable Long visitorId,
            @RequestBody Map<String, String> body) {
        String reason = body.getOrDefault("reason", "No reason provided");
        String createdBy = body.getOrDefault("createdBy", "Admin");
        return ResponseEntity.ok(blacklistService.addToBlacklist(visitorId, reason, createdBy));
    }

    @DeleteMapping("/{visitorId}")
    public ResponseEntity<Void> removeFromBlacklist(@PathVariable Long visitorId) {
        blacklistService.removeFromBlacklist(visitorId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check/{visitorId}")
    public ResponseEntity<Map<String, Boolean>> checkBlacklist(@PathVariable Long visitorId) {
        return ResponseEntity.ok(Map.of("blacklisted", blacklistService.isBlacklisted(visitorId)));
    }
}
