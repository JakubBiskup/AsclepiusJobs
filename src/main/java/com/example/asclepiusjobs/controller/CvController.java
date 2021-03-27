package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.dto.*;
import com.example.asclepiusjobs.model.*;
import com.example.asclepiusjobs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
public class CvController {

    @Autowired
    private CvService cvService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private SkillService skillService;


    @PatchMapping(value = "cv/change-visibility")
    ResponseEntity changeMyCvVisibility(@RequestBody boolean makeVisible) throws Exception {
        Cv myCv=getMyCv();
        myCv.setVisibility(makeVisible);
        cvService.saveOrUpdate(myCv);
        if (makeVisible==true) {
            return ResponseEntity.ok("Your CV is now visible to other users.");
        }else {
            return ResponseEntity.ok("Your CV is now invisible to other users.");
        }
    }

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

    @PatchMapping(value = "/skill/update")
    ResponseEntity updateSkillsAndTheirOrder(@RequestBody List<String> skillsInOrder){
        Cv myCv=getMyCv();
        skillService.clearSkillsAndTheirOrderOnCv(myCv);
        List<Skill> skillsList=new ArrayList<>();
        for(String nameOfSkill:skillsInOrder){
            Skill skill= skillService.getByNameOrReturnNull(nameOfSkill);
            if(skill==null){
                skill=skillService.createSkill(nameOfSkill);
            }
            skillsList.add(skill);
        }
        cvService.updateSkillsAndTheirOrder(myCv,skillsList);


        //code below is for confirming the right order of skills, remove/replace later

        List<Skill> returnedSkillsList=skillService.getSkillsInOrderOnCv(myCv);

        int returnedListSize= returnedSkillsList.size();
        switch (returnedListSize){
            case 0:
                return ResponseEntity.ok("You have not entered any skills.");
            case 1:
                return ResponseEntity.ok("Your only skill is: " +returnedSkillsList.get(0).getName());
            case 2:
                return ResponseEntity.ok("Both of your skills will be visible in listing results ("
                        +returnedSkillsList.get(0).getName()
                        +" and "
                        +returnedSkillsList.get(1).getName()
                        +").");
        }

        StringBuilder returnedStringBuilder= new StringBuilder("These skills will be shown in listing results: ");
        returnedStringBuilder.append(returnedSkillsList.get(0).getName());
        returnedStringBuilder.append(",");
        returnedStringBuilder.append(returnedSkillsList.get(1).getName());
        returnedStringBuilder.append(" and ");
        returnedStringBuilder.append(returnedSkillsList.get(2).getName());
        if(returnedListSize>3) {
            returnedStringBuilder.append(". These will be visible only after opening your CV: ");
            for (int i = 3; i < returnedListSize; i++) {
                returnedStringBuilder.append(" - ").append(returnedSkillsList.get(i).getName());
            }
        }
        return ResponseEntity.ok(returnedStringBuilder.toString());

        //



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
        if(experienceService.getById(id).getCv().equals(myCv)){
            experienceService.deleteById(id);
            return ResponseEntity.ok("Experience deleted.");
        }else {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping(value = "/cv/update")
    ResponseEntity replaceMyWholeCv(@Valid @RequestBody CompleteCvDto completeCvDto) throws Exception {

        Cv myCv=clearCv(getMyCv());

        cvService.updateBasics(myCv.getId(),completeCvDto.getBasicCvFields());

        List<String> skillsInOrder=completeCvDto.getSkillNamesList();
        List<Skill> skillsList=new ArrayList<>();
        for(String nameOfSkill:skillsInOrder){
            Skill skill= skillService.getByNameOrReturnNull(nameOfSkill);
            if(skill==null){
                skill=skillService.createSkill(nameOfSkill);
            }
            skillsList.add(skill);
        }
        cvService.updateSkillsAndTheirOrder(myCv,skillsList);

        for(LanguageDto languageDto:completeCvDto.getLanguageList()){
            languageService.createLanguage(myCv,languageDto);
        }

        for (EducationDto educationDto:completeCvDto.getEducationList()){
            educationService.createEducation(myCv,educationDto);
        }
        for (ExperienceDto experienceDto:completeCvDto.getExperienceList()){
            experienceService.createExperience(experienceDto,myCv);
        }
        cvService.saveOrUpdate(myCv);
        return ResponseEntity.ok("Your CV was updated.");
    }

    private Cv getMyCv(){
        return getAuthenticatedUser().getCv();
    }

    private User getAuthenticatedUser(){
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(loggedInUserEmail);
    }

    private Cv clearCv(Cv cv) throws Exception {

        Set emptySet=new HashSet();

        Set<Experience> experienceSet=cv.getExperienceSet();
        for(Experience experience:experienceSet){
            experienceService.deleteById(experience.getId());
        }
        cv.setExperienceSet(emptySet);

        Set<Education> educationSet=cv.getEducationSet();
        for(Education education:educationSet){
            educationService.delete(education);
        }
        cv.setEducationSet(emptySet);
        Set<Language> languageSet=cv.getLanguages();
        for(Language language:languageSet){
            languageService.deleteById(language.getId());
        }
        cv.setLanguages(emptySet);
        skillService.clearSkillsAndTheirOrderOnCv(cv);
        CvDto startCv=new CvDto();
        startCv.setAvailable(false);
        startCv.setTitle(null);
        startCv.setInterests(null);
        return cvService.updateBasics(cv.getId(),startCv);

    }


}
