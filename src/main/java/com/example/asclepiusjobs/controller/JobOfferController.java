package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.dto.JobOfferCriteriaDto;
import com.example.asclepiusjobs.dto.JobOfferResultDto;
import com.example.asclepiusjobs.dto.SearchResultJobOffersDto;
import com.example.asclepiusjobs.model.JobOffer;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.service.JobOfferService;
import com.example.asclepiusjobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/job-offer/search")
    public SearchResultJobOffersDto searchJobOffers(@RequestBody JobOfferCriteriaDto searchCriteria){
        return jobOfferService.getSearchResultJobOffers(searchCriteria);
    }

    @GetMapping(value = "job-offer/fav")
    public SearchResultJobOffersDto getMyFavouriteJobOffers(){
        Set<JobOffer> jobOfferSet= getAuthenticatedUser().getFavouriteJobOffers();
        List<JobOfferResultDto> resultList=new ArrayList<>();
        for(JobOffer jobOffer:jobOfferSet){
            resultList.add(jobOfferService.convertJobOfferEntityToResultDto(jobOffer));
        }
        SearchResultJobOffersDto finalResult=new SearchResultJobOffersDto();
        finalResult.setResults(resultList);
        finalResult.setResultsCount(resultList.size());
        return finalResult;
    }

    @PostMapping(value = "/job-offer/{id}/mark-as-fav")
    public ResponseEntity addToUserFav(@PathVariable Long id) throws Exception {
        jobOfferService.markJobOfferAsUserFavourite(jobOfferService.getJobOfferById(id),getAuthenticatedUser());
        return ResponseEntity.ok("Job offer added to favorites");

    }

    private User getAuthenticatedUser(){
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(loggedInUserEmail);
    }
}
