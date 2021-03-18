package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.dto.CvDto;
import com.example.asclepiusjobs.dto.ExperienceDto;
import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.service.CvService;
import com.example.asclepiusjobs.service.ExperienceService;
import com.example.asclepiusjobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class CvController {

    @Autowired
    CvService cvService;

    @Autowired
    UserService userService;

    @Autowired
    ExperienceService experienceService;

    @PatchMapping(value = "/update-basic-cv-elements")
    ResponseEntity updateBasicsOnCv(@RequestBody CvDto cvDto) throws Exception {
        cvService.updateBasics(getAuthenticatedUser().getId(),cvDto);
        return ResponseEntity.ok("Cv updated.");
    }

    @PostMapping(value = "/add-experience-to-my-cv")
    ResponseEntity addExperienceToMyCv(@RequestBody ExperienceDto experienceDto) throws Exception {
        Long loggedInUserId=getAuthenticatedUser().getId();
        Cv cv=cvService.getCvById(loggedInUserId);
        experienceService.createExperience(experienceDto,cv);
        return ResponseEntity.ok("Experience added to your CV.");
    }


    private User getAuthenticatedUser(){
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(loggedInUserEmail);
    }


}
