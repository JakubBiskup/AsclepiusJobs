package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.dto.JobOfferCriteriaDto;
import com.example.asclepiusjobs.model.JobOffer;
import com.example.asclepiusjobs.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class JobOfferController {

    @Autowired
    JobOfferService jobOfferService;

    @GetMapping(value = "/job-offers/search")
    public List<JobOffer> searchForJobOffers(@RequestBody JobOfferCriteriaDto searchCriteria){
        return jobOfferService.findJobOffersWithCriteria(searchCriteria);
    }

}
