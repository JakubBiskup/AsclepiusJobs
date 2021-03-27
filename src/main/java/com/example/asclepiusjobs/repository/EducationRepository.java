package com.example.asclepiusjobs.repository;

import com.example.asclepiusjobs.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education,Long> {
    void deleteById(Long id);
}
