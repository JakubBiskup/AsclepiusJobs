package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.*;
import com.example.asclepiusjobs.model.*;
import com.example.asclepiusjobs.repository.CvRepository;
import com.example.asclepiusjobs.repository.SkillOrderOnCvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CvService {

    @Autowired
    private CvRepository cvRepository;

    @Autowired
    private SkillOrderOnCvRepository skillOrderOnCvRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private LanguageService languageService;


    public CompleteCvDto getCompleteDtoOfCv(Cv cv){
        CompleteCvDto completeCvDto=new CompleteCvDto();

        CvDto basicCvFieldsDto=new CvDto();
        basicCvFieldsDto.setInterests(cv.getInterests());
        basicCvFieldsDto.setTitle(cv.getTitle());
        basicCvFieldsDto.setAvailable(cv.isAvailable());
        completeCvDto.setBasicCvFields(basicCvFieldsDto);

        Set<Education> educationSet=cv.getEducationSet();
        List<EducationDto> educationDtoList=new ArrayList<>();
        for(Education education:educationSet){
            EducationDto educationDto= educationService.convertEducationEntityToDto(education);
            educationDtoList.add(educationDto);
        }
        completeCvDto.setEducationList(educationDtoList);

        Set<Language> languageSet=cv.getLanguages();
        List<LanguageDto> languageDtoList=new ArrayList<>();
        for(Language language:languageSet){
            LanguageDto languageDto=languageService.convertLanguageEntityToDto(language);
            languageDtoList.add(languageDto);
        }
        completeCvDto.setLanguageList(languageDtoList);

        Set<Experience> experienceSet=cv.getExperienceSet();
        List<ExperienceDto> experienceDtoList=new ArrayList<>();
        for (Experience experience:experienceSet){
            ExperienceDto experienceDto=experienceService.convertExperienceEntityToDto(experience);
            experienceDtoList.add(experienceDto);
        }
        completeCvDto.setExperienceList(experienceDtoList);


        List<Skill> skillsList=skillService.getSkillsInOrderOnCv(cv);
        List<String> skillNamesList=new ArrayList<>();
        for(Skill skill:skillsList){
            skillNamesList.add(skill.getName());
        }
        completeCvDto.setSkillNamesList(skillNamesList);


        return completeCvDto;
    }

    public Cv createBlankInvisibleCv(User user){
        Cv cv=new Cv();
        cv.setUser(user);
        cv.setVisibility(false);
        Cv savedCv= cvRepository.save(cv);
        user.setCv(cv);
        userService.saveOrUpdate(user);
        return savedCv;
    }

    public Cv updateBasics(Long id, CvDto cvDto) throws Exception {
        Cv cv=getCvById(id);
        cv.setAvailable(cvDto.isAvailable());
        cv.setTitle(cvDto.getTitle());
        cv.setInterests(cvDto.getInterests());
        return saveOrUpdate(cv);
    }

    public Cv updateSkillsAndTheirOrder(Cv cv, List<Skill> skillsInOrder){
        List<SkillOrderOnCv> skillOrderOnCvList = new ArrayList<>();
        int appearanceOrder=0;
        for(Skill skill:skillsInOrder){
            SkillOrderOnCv skillOrderOnCv=new SkillOrderOnCv();
            skillOrderOnCv.setCv(cv);
            skillOrderOnCv.setSkill(skill);
            skillOrderOnCv.setAppearanceOrder(appearanceOrder);
            SkillOrderOnCv savedSkillOrder=skillOrderOnCvRepository.save(skillOrderOnCv);
            skillOrderOnCvList.add(savedSkillOrder);
            appearanceOrder++;
        }
        cv.setSkills(skillOrderOnCvList);
        return saveOrUpdate(cv);
    }

    public Cv getCvById(Long id) throws Exception {
        Optional<Cv> optionalCv = cvRepository.findById(id);
        if (optionalCv.isPresent()) {
            return optionalCv.get();
        } else {
            throw new Exception("No CV found");
        }
    }

    public  Cv saveOrUpdate(Cv cv){
        return cvRepository.save(cv);
    }

}
