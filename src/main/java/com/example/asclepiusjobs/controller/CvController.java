package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.dto.CvDto;
import com.example.asclepiusjobs.dto.EducationDto;
import com.example.asclepiusjobs.dto.ExperienceDto;
import com.example.asclepiusjobs.dto.LanguageDto;
import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class CvController {

    @Autowired
    CvService cvService;

    @Autowired
    UserService userService;

    @Autowired
    ExperienceService experienceService;

    @Autowired
    LanguageService languageService;

    @Autowired
    EducationService educationService;


    @PatchMapping(value = "/update-basic-cv-elements")
    ResponseEntity updateBasicsOnCv(@RequestBody CvDto cvDto) throws Exception {
        cvService.updateBasics(getAuthenticatedUser().getId(),cvDto);
        return ResponseEntity.ok("Cv updated.");
    }

    @PostMapping(value = "/experience/add")
    ResponseEntity addExperienceToMyCv(@Valid @RequestBody ExperienceDto experienceDto) throws Exception {
        Cv cv= getMyCv();
        experienceService.createExperience(experienceDto,cv);
        return ResponseEntity.ok("Experience added to your CV.");
    }

    @PostMapping(value = "/language/add")
    ResponseEntity addLanguageToMyCv(@Valid @RequestBody LanguageDto languageDto){
        Cv myCv= getMyCv();
        languageService.createLanguage(myCv,languageDto);
        return ResponseEntity.ok("Language added to your CV.");
    }

    @PostMapping(value = "/education/add")
    ResponseEntity addEducationToMyCv(@Valid @RequestBody EducationDto educationDto) throws Exception {
        Cv myCv= getMyCv();
        educationService.createEducation(myCv,educationDto);
        return ResponseEntity.ok("Education added to your CV");
    }

    @DeleteMapping(value = "/education/{id}")
    ResponseEntity deleteMyEducation(@PathVariable Long id) throws Exception {
        Cv myCv=getMyCv();
        if (educationService.getById(id).getCv().equals(myCv)){
            educationService.deleteById(id);
            return ResponseEntity.ok("Education deleted from your CV.");
        }else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping(value = "/language/{id}")
    ResponseEntity deleteMyLanguage(@PathVariable Long id) throws Exception {
        Cv myCv=getMyCv();
        if(languageService.getById(id).getCv().equals(myCv)){
            languageService.deleteById(id);
            return ResponseEntity.ok("Language deleted from your CV.");
        }else{
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping(value = "/experience/{id}")
    ResponseEntity deleteMyExperience(@PathVariable Long id) throws Exception {
        Cv myCv=getMyCv();
        if(experienceService.getById(id).getCv().getId().equals(myCv)){
            experienceService.deleteById(id);
            return ResponseEntity.ok("Experience deleted.");
        }else {
            return ResponseEntity.status(403).build();
        }
    }

    private Cv getMyCv(){
        return getAuthenticatedUser().getCv();
    }

    private User getAuthenticatedUser(){
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(loggedInUserEmail);
    }


}
