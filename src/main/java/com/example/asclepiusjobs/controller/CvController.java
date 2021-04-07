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

    @GetMapping(value = "cv/my-cv")
    public CompleteCvDto describeMyCv(){
        Cv myCv=getMyCv();
        return cvService.getCompleteDtoOfCv(myCv);

    }

    @PatchMapping(value = "cv/change-visibility")
    public ResponseEntity changeMyCvVisibility(@RequestBody boolean makeVisible) {
        Cv myCv=getMyCv();
        myCv.setVisibility(makeVisible);
        cvService.saveOrUpdate(myCv);
        if (makeVisible) {
            return ResponseEntity.ok("Your CV is now visible to other users.");
        }else {
            return ResponseEntity.ok("Your CV is now invisible to other users.");
        }
    }

    @PatchMapping(value = "/update-basic-cv-elements")
    public ResponseEntity updateBasicsOnCv(@RequestBody CvDto cvDto) throws Exception {
        cvService.updateBasics(getAuthenticatedUser().getId(),cvDto);
        return ResponseEntity.ok("Cv updated.");
    }

    @PostMapping(value = "/experience/add")
    public ResponseEntity addExperienceToMyCv(@Valid @RequestBody ExperienceDto experienceDto) throws Exception {
        Cv cv= getMyCv();
        experienceService.createExperience(experienceDto,cv);
        return ResponseEntity.ok("Experience added to your CV.");
    }

    @PostMapping(value = "/language/add")
    public ResponseEntity addLanguageToMyCv(@Valid @RequestBody LanguageDto languageDto){
        Cv myCv= getMyCv();
        languageService.createLanguage(myCv,languageDto);
        return ResponseEntity.ok("Language added to your CV.");
    }

    @PostMapping(value = "/education/add")
    public ResponseEntity addEducationToMyCv(@Valid @RequestBody EducationDto educationDto) throws Exception {
        Cv myCv= getMyCv();
        educationService.createEducation(myCv,educationDto);
        return ResponseEntity.ok("Education added to your CV");
    }

    @PatchMapping(value = "/skill/update")
    public ResponseEntity updateSkillsAndTheirOrder(@RequestBody List<String> skillsInOrder){
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
        return ResponseEntity.ok("Skills updated.");
    }

    @DeleteMapping(value = "/education/{id}")
    public ResponseEntity deleteMyEducation(@PathVariable Long id) throws Exception {
        Cv myCv=getMyCv();
        if (educationService.getById(id).getCv().equals(myCv)){
            educationService.deleteById(id);
            return ResponseEntity.ok("Education deleted from your CV.");
        }else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping(value = "/language/{id}")
    public ResponseEntity deleteMyLanguage(@PathVariable Long id) throws Exception {
        Cv myCv=getMyCv();
        if(languageService.getById(id).getCv().equals(myCv)){
            languageService.deleteById(id);
            return ResponseEntity.ok("Language deleted from your CV.");
        }else{
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping(value = "/experience/{id}")
    public ResponseEntity deleteMyExperience(@PathVariable Long id) throws Exception {
        Cv myCv=getMyCv();
        if(experienceService.getById(id).getCv().equals(myCv)){
            experienceService.deleteById(id);
            return ResponseEntity.ok("Experience deleted.");
        }else {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping(value = "/cv/update")
    public ResponseEntity replaceMyWholeCv(@Valid @RequestBody CompleteCvDto completeCvDto) throws Exception {

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
