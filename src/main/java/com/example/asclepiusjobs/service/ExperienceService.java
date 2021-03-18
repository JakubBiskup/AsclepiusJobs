package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.ExperienceDto;
import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.Experience;
import com.example.asclepiusjobs.model.Location;
import com.example.asclepiusjobs.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExperienceService {

    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    LocationService locationService;

    public Experience createExperience(ExperienceDto experienceDto, Cv cv) throws Exception {
        Date startDate=experienceDto.getStartDate();
        Date endDate=experienceDto.getEndDate();
        if(endDate!=null) {
            if (endDate.before(startDate)) {
                throw new Exception("Start date must be before end date.");
            }
        }
        Experience newExperience=new Experience();
        Location newLocation=locationService.createLocationFromLocationDto(experienceDto.getLocationDto());
        newExperience.setLocation(newLocation);
        newExperience.setCompanyName(experienceDto.getCompanyName());
        newExperience.setDescription(experienceDto.getDescription());
        newExperience.setPositionTitle(experienceDto.getPositionTitle());
        newExperience.setStartDate(experienceDto.getStartDate());
        newExperience.setEndDate(experienceDto.getEndDate());
        newExperience.setCv(cv);
        return experienceRepository.save(newExperience);
    }
}
