package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.JobOfferCriteriaDto;
import com.example.asclepiusjobs.dto.JobOfferResultDto;
import com.example.asclepiusjobs.dto.LocationDto;
import com.example.asclepiusjobs.dto.SearchResultJobOffersDto;
import com.example.asclepiusjobs.model.JobOffer;
import com.example.asclepiusjobs.model.Skill;
import com.example.asclepiusjobs.model.enums.Profession;
import com.example.asclepiusjobs.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Autowired
    private LocationService locationService;

    @PersistenceContext
    private EntityManager entityManager;

    public List<JobOffer> getAllJobOffers(){
        return jobOfferRepository.findAll();
    }

    public SearchResultJobOffersDto getSearchResultJobOffers(JobOfferCriteriaDto criteriaDto){
        List<JobOffer> jobOffers=findJobOffersWithCriteria(criteriaDto);
        List<JobOfferResultDto> jobOfferResults=new ArrayList<>();
        for(JobOffer jobOffer:jobOffers){
            jobOfferResults.add(convertJobOfferEntityToResultDto(jobOffer));
        }
        SearchResultJobOffersDto finalResult=new SearchResultJobOffersDto();
        finalResult.setResults(jobOfferResults);
        finalResult.setResultsCount(jobOfferResults.size());
        return finalResult;
    }

    public JobOfferResultDto convertJobOfferEntityToResultDto(JobOffer entity){
        JobOfferResultDto resultDto=new JobOfferResultDto();
        resultDto.setId(entity.getId());
        resultDto.setTitle(entity.getTitle());
        resultDto.setMissionStartDate(entity.getMissionStartDate());
        resultDto.setProfession(entity.getProfession());
        resultDto.setDurationInMonths(entity.getDuration());
        resultDto.setMonthlySalary(entity.getSalary());
        resultDto.setMission(entity.getMission());
        resultDto.setDescription(entity.getDescription());
        resultDto.setYearsOfExperienceRequired(entity.getExperienceRequired());
        resultDto.setContactEmail(entity.getEmail());
        resultDto.setCreateTime(entity.getCreateTime());

        LocationDto locationDto= locationService.convertLocationEntityToDto(entity.getLocation());
        resultDto.setLocation(locationDto);

        Set<Skill> skillSet=entity.getSkillsRequired();
        List<String> skillNamesList=new ArrayList<>();
        for(Skill skill:skillSet){
            skillNamesList.add(skill.getName());
        }
        resultDto.setSkillsRequired(skillNamesList);

        return resultDto;

    }

    public List<JobOffer> findJobOffersWithCriteria(JobOfferCriteriaDto criteriaDto){
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<JobOffer> criteriaQuery=criteriaBuilder.createQuery(JobOffer.class);
        Root<JobOffer> jobOfferRoot=criteriaQuery.from(JobOffer.class);

        List<Predicate> predicatesList=new ArrayList<>();

        Predicate visiblePredicate=criteriaBuilder.equal(jobOfferRoot.get("visibility"),true);
        predicatesList.add(visiblePredicate);

        String countryName= criteriaDto.getCountry();
        if(countryName!=null){
            Predicate countryPredicate=criteriaBuilder.like(jobOfferRoot.get("location").get("country"),"%"+countryName+"%");
            predicatesList.add(countryPredicate);
        }
        String cityName=criteriaDto.getCity();
        if(cityName!=null){
            Predicate cityPredicate=criteriaBuilder.like(jobOfferRoot.get("location").get("city"),"%"+cityName+"%");
            predicatesList.add(cityPredicate);
        }
        String title=criteriaDto.getTitle();
        if(title!=null){
            Predicate titlePredicate=criteriaBuilder.like(jobOfferRoot.get("title"),"%"+title+"%");
            predicatesList.add(titlePredicate);
        }
        Profession profession=criteriaDto.getProfession();
        if(profession!=null){
            Predicate professionPredicate=criteriaBuilder.equal(jobOfferRoot.get("profession"),profession);
            predicatesList.add(professionPredicate);
        }
        Integer maxDaysSinceCreation=criteriaDto.getMaxDaysSinceCreation();
        if(maxDaysSinceCreation!=null){
            Calendar calendar= GregorianCalendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR,-maxDaysSinceCreation);
            Date someDaysAgo=calendar.getTime();
            Predicate notOlderThanPredicate=criteriaBuilder.greaterThanOrEqualTo(jobOfferRoot.get("createTime"),someDaysAgo);
            predicatesList.add(notOlderThanPredicate);
        }
        Integer minExperience=criteriaDto.getMinExperience();
        if(minExperience!=null){
            Predicate minExpPredicate=criteriaBuilder.greaterThanOrEqualTo(jobOfferRoot.get("experienceRequired"),minExperience);
            predicatesList.add(minExpPredicate);
        }
        Integer maxExperience=criteriaDto.getMaxExperience();
        if(maxExperience!=null){
            Predicate maxExpPredicate=criteriaBuilder.lessThanOrEqualTo(jobOfferRoot.get("experienceRequired"),maxExperience);
            predicatesList.add(maxExpPredicate);
        }
        Integer minSalary=criteriaDto.getMinSalary();
        if(minSalary!=null){
            Predicate minSalaryPredicate=criteriaBuilder.greaterThanOrEqualTo(jobOfferRoot.get("salary"),minSalary);
            predicatesList.add(minSalaryPredicate);
        }
        Integer maxSalary= criteriaDto.getMaxSalary();
        if(maxSalary!=null){
            Predicate maxSalaryPredicate=criteriaBuilder.lessThanOrEqualTo(jobOfferRoot.get("salary"),maxSalary);
            predicatesList.add(maxSalaryPredicate);
        }
                                    //health Establishment predicate




        criteriaQuery.where(predicatesList.toArray(new Predicate[]{}));

        Boolean olderFirst=criteriaDto.getShowOlderFirst();
        if(olderFirst!=null && olderFirst){
            criteriaQuery.orderBy(criteriaBuilder.asc(jobOfferRoot.get("createTime")));
        }else{
            criteriaQuery.orderBy(criteriaBuilder.desc(jobOfferRoot.get("createTime")));
        }


        TypedQuery<JobOffer> query=entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }
}
