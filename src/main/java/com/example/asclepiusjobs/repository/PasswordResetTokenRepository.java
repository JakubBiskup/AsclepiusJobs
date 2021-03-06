package com.example.asclepiusjobs.repository;

import com.example.asclepiusjobs.model.PasswordResetToken;
import com.example.asclepiusjobs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByExpirationDateLessThan(Date now);
    void deleteByUser(User user);
}
