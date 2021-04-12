package com.example.asclepiusjobs.repository;

import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);
    void deleteByExpirationDateLessThan(Date now);
    Optional<VerificationToken> findByUser(User user);
}
