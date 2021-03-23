package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.EducationDto;
import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.Education;
import com.example.asclepiusjobs.model.Location;
import com.example.asclepiusjobs.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class EducationService {

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    LocationService locationService;

    public Education createEducation(Cv cv, EducationDto educationDto) throws Exception {
        Date startDate=educationDto.getStartDate();
        Date endDate=educationDto.getEndDate();
        if(endDate!=null){
            if(endDate.before(startDate)){
                throw new Exception("Start date must be before end date.");
            }
        }
        Education newEducation=new Education();

        newEducation.setCv(cv);

        newEducation.setStartDate(startDate);
        newEducation.setEndDate(endDate);
        newEducation.setDiplomaTitle(educationDto.getDiplomaTitle());
        newEducation.setEducationalLevel(educationDto.getEducationalLevel());
        newEducation.setOrganization(educationDto.getOrganization());

        Location location=locationService.createLocationFromLocationDto(educationDto.getLocationDto());
        newEducation.setLocation(location);

        return educationRepository.save(newEducation);
    }

    public Education getById(Long id) throws Exception {
        Optional<Education> optionalEducation = educationRepository.findById(id);
        if(optionalEducation.isPresent()){
            return optionalEducation.get();
        }else {
            throw new Exception("Education not found");
        }
    }

    public void deleteById(Long id){
        educationRepository.deleteById(id);
    }
}
