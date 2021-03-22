package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.dto.CvDto;
import com.example.asclepiusjobs.dto.ExperienceDto;
import com.example.asclepiusjobs.dto.LanguageDto;
import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.Language;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.service.CvService;
import com.example.asclepiusjobs.service.ExperienceService;
import com.example.asclepiusjobs.service.LanguageService;
import com.example.asclepiusjobs.service.UserService;
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


    @PatchMapping(value = "/update-basic-cv-elements")
    ResponseEntity updateBasicsOnCv(@RequestBody CvDto cvDto) throws Exception {
        cvService.updateBasics(getAuthenticatedUser().getId(),cvDto);
        return ResponseEntity.ok("Cv updated.");
    }

    @PostMapping(value = "/experience/add")
    ResponseEntity addExperienceToMyCv(@Valid @RequestBody ExperienceDto experienceDto) throws Exception {
        Cv cv= getAuthenticatedUser().getCv();
        experienceService.createExperience(experienceDto,cv);
        return ResponseEntity.ok("Experience added to your CV.");
    }

    @PostMapping(value = "/language/add")
    ResponseEntity addLanguageToMyCv(@Valid @RequestBody LanguageDto languageDto){
        Cv myCv= getAuthenticatedUser().getCv();
        languageService.createLanguage(myCv,languageDto);
        return ResponseEntity.ok("Language added to your CV.");
    }

    @DeleteMapping(value = "/language/{id}")
    ResponseEntity deleteMyLanguage(@PathVariable Long id) throws Exception {
        Cv myCv=getAuthenticatedUser().getCv();
        if(languageService.getById(id).getCv().equals(myCv)){
            languageService.deleteById(id);
            return ResponseEntity.ok("Language deleted from your CV.");
        }else{
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping(value = "/experience/{id}")
    ResponseEntity deleteMyExperience(@PathVariable Long id) throws Exception {
        Cv myCv=getAuthenticatedUser().getCv();
        if(experienceService.getById(id).getCv().getId().equals(myCv)){
            experienceService.deleteById(id);
            return ResponseEntity.ok("Experience deleted.");
        }else {
            return ResponseEntity.status(403).build();
        }
    }

    private User getAuthenticatedUser(){
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(loggedInUserEmail);
    }


}
