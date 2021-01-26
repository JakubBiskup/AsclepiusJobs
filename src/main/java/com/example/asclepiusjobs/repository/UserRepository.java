package com.example.asclepiusjobs.repository;

import com.example.asclepiusjobs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByIdentifying(String identifying);
}
