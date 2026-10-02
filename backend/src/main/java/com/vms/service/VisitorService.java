package com.vms.service;

import com.vms.model.Visitor;
import com.vms.repository.BlacklistRepository;
import com.vms.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private BlacklistRepository blacklistRepository;

    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    public Optional<Visitor> getVisitorById(Long id) {
        return visitorRepository.findById(id);
    }

    // Used by returning-visitor detection — search by phone OR email
    public Optional<Visitor> findByPhoneOrEmail(String phone, String email) {
        return visitorRepository.findByPhoneOrEmail(phone, email);
    }

    public Visitor createVisitor(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    public Visitor updateVisitor(Long id, Visitor details) {
        Visitor visitor = visitorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Visitor not found"));
        visitor.setFirstName(details.getFirstName());
        visitor.setLastName(details.getLastName());
        visitor.setPhone(details.getPhone());
        visitor.setEmail(details.getEmail());
        visitor.setCompanyName(details.getCompanyName());
        visitor.setIdProofType(details.getIdProofType());
        visitor.setIdProofNumber(details.getIdProofNumber());
        visitor.setAddress(details.getAddress());
        return visitorRepository.save(visitor);
    }

    public void deleteVisitor(Long id) {
        visitorRepository.deleteById(id);
    }

    public boolean isBlacklisted(Long visitorId) {
        return blacklistRepository.existsByVisitorId(visitorId);
    }

    public List<Visitor> searchVisitors(String query) {
        return visitorRepository
            .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
    }
}
