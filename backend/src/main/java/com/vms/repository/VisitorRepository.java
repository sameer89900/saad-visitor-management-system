package com.vms.repository;

import com.vms.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findByPhone(String phone);

    Optional<Visitor> findByEmail(String email);

    Optional<Visitor> findByPhoneOrEmail(String phone, String email);

    List<Visitor> findByIsBlacklisted(Boolean isBlacklisted);

    List<Visitor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);
}
