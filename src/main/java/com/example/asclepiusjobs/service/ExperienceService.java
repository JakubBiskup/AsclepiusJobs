package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.ExperienceDto;
import com.example.asclepiusjobs.dto.LocationDto;
import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.Experience;
import com.example.asclepiusjobs.model.Location;
import com.example.asclepiusjobs.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private LocationService locationService;

    public ExperienceDto convertExperienceEntityToDto(Experience experienceEntity){
        ExperienceDto experienceDto=new ExperienceDto();
        experienceDto.setCompanyName(experienceEntity.getCompanyName());
        experienceDto.setDescription(experienceEntity.getDescription());
        experienceDto.setPositionTitle(experienceEntity.getPositionTitle());
        experienceDto.setStartDate(experienceEntity.getStartDate());
        experienceDto.setEndDate(experienceEntity.getEndDate());

        LocationDto experienceLocationDto= locationService.convertLocationEntityToDto(experienceEntity.getLocation());
        experienceDto.setLocationDto(experienceLocationDto);

        return experienceDto;
    }

    public Experience createExperience(ExperienceDto experienceDto, Cv cv) throws Exception {
        Date startDate=experienceDto.getStartDate();
        Date endDate=experienceDto.getEndDate();
        if(endDate!=null&&endDate.before(startDate)) {
            throw new Exception("Start date must be before end date.");
        }
        Experience newExperience=new Experience();
        Location newLocation=locationService.createLocationFromLocationDto(experienceDto.getLocationDto());
        newExperience.setLocation(newLocation);
        newExperience.setCompanyName(experienceDto.getCompanyName());
        newExperience.setDescription(experienceDto.getDescription());
        newExperience.setPositionTitle(experienceDto.getPositionTitle());
        newExperience.setStartDate(startDate);
        newExperience.setEndDate(endDate);
        newExperience.setCv(cv);
        return experienceRepository.save(newExperience);
    }

    public Experience getById(Long id) throws Exception {
        Optional<Experience> optionalExperience = experienceRepository.findById(id);
        if(optionalExperience.isPresent()){
            return optionalExperience.get();
        }else {
            throw new Exception("Experience not found");
        }
    }

    public void deleteById(Long id){
        experienceRepository.deleteById(id);
    }
}
