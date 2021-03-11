package com.example.asclepiusjobs.repository;

import com.example.asclepiusjobs.model.JobApplication;
import com.example.asclepiusjobs.model.JobApplicationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, JobApplicationId> {
}
