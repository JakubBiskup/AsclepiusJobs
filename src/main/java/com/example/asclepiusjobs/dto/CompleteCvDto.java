package com.example.asclepiusjobs.dto;

import javax.validation.Valid;
import java.util.List;

public class CompleteCvDto {

    @Valid
    private CvDto basicCvFields;
    //photo here ?
    private List< @Valid ExperienceDto> experienceList;
    private List< @Valid EducationDto> educationList;
    private List< @Valid LanguageDto> languageList;
    private List<String> skillNamesList;

    public CvDto getBasicCvFields() {
        return basicCvFields;
    }

    public void setBasicCvFields(CvDto basicCvFields) {
        this.basicCvFields = basicCvFields;
    }

    public List<ExperienceDto> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<ExperienceDto> experienceList) {
        this.experienceList = experienceList;
    }

    public List<EducationDto> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EducationDto> educationList) {
        this.educationList = educationList;
    }

    public List<LanguageDto> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<LanguageDto> languageList) {
        this.languageList = languageList;
    }

    public List<String> getSkillNamesList() {
        return skillNamesList;
    }

    public void setSkillNamesList(List<String> skillNamesList) {
        this.skillNamesList = skillNamesList;
    }
}
