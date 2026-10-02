package com.vms.service;

import com.vms.model.Blacklist;
import com.vms.model.Visitor;
import com.vms.repository.BlacklistRepository;
import com.vms.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BlacklistService {

    @Autowired
    private BlacklistRepository blacklistRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    public List<Blacklist> getAllBlacklisted() {
        return blacklistRepository.findAll();
    }

    public Blacklist addToBlacklist(Long visitorId, String reason, String createdBy) {
        Visitor visitor = visitorRepository.findById(visitorId)
            .orElseThrow(() -> new RuntimeException("Visitor not found"));

        visitor.setIsBlacklisted(true);
        visitorRepository.save(visitor);

        Blacklist entry = new Blacklist();
        entry.setVisitor(visitor);
        entry.setReason(reason);
        entry.setCreatedBy(createdBy);
        return blacklistRepository.save(entry);
    }

    public void removeFromBlacklist(Long visitorId) {
        Blacklist entry = blacklistRepository.findByVisitorId(visitorId)
            .orElseThrow(() -> new RuntimeException("Visitor not in blacklist"));

        Visitor visitor = entry.getVisitor();
        visitor.setIsBlacklisted(false);
        visitorRepository.save(visitor);

        blacklistRepository.delete(entry);
    }

    public boolean isBlacklisted(Long visitorId) {
        return blacklistRepository.existsByVisitorId(visitorId);
    }
}
