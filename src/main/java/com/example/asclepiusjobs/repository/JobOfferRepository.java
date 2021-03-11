package com.example.asclepiusjobs.repository;

import com.example.asclepiusjobs.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer,Long> {
}
