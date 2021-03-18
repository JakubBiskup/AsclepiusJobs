package com.example.asclepiusjobs.repository;

import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CvRepository extends JpaRepository<Cv,Long> {
    Optional<Cv> findByUser(User user);
}
