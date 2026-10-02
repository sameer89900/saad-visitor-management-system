package com.vms.controller;

import com.vms.model.Visitor;
import com.vms.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visitors")
@CrossOrigin(origins = "http://localhost:3000")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        return ResponseEntity.ok(visitorService.getAllVisitors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visitor> getVisitorById(@PathVariable Long id) {
        return visitorService.getVisitorById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Returning-visitor detection endpoint — used by frontend live lookup
    @GetMapping("/search")
    public ResponseEntity<Visitor> searchByPhoneOrEmail(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email) {
        return visitorService.findByPhoneOrEmail(
                phone != null ? phone : "",
                email != null ? email : "")
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Visitor> createVisitor(@RequestBody Visitor visitor) {
        return ResponseEntity.ok(visitorService.createVisitor(visitor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visitor> updateVisitor(@PathVariable Long id,
                                                  @RequestBody Visitor visitor) {
        return ResponseEntity.ok(visitorService.updateVisitor(id, visitor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitor(@PathVariable Long id) {
        visitorService.deleteVisitor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/blacklisted")
    public ResponseEntity<Map<String, Boolean>> isBlacklisted(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("blacklisted", visitorService.isBlacklisted(id)));
    }
}
