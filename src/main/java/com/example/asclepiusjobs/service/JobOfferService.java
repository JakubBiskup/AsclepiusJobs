package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.*;
import com.example.asclepiusjobs.model.JobOffer;
import com.example.asclepiusjobs.model.Skill;
import com.example.asclepiusjobs.model.User;
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

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    public List<JobOffer> getAllJobOffers(){
        return jobOfferRepository.findAll();
    }

    public void markJobOfferAsUserFavourite(JobOffer jobOffer, User user){
        Set<JobOffer> favouriteJobOffers=user.getFavouriteJobOffers();
        favouriteJobOffers.add(jobOffer);
        user.setFavouriteJobOffers(favouriteJobOffers);
        userService.saveOrUpdate(user);
    }

    public void removeJobOfferFromUserFavorites(JobOffer jobOffer,User user){
        Set<JobOffer> favouriteJobOffers=user.getFavouriteJobOffers();
        favouriteJobOffers.remove(jobOffer);
        user.setFavouriteJobOffers(favouriteJobOffers);
        userService.saveOrUpdate(user);
    }

    public JobOffer getJobOfferById(Long id) throws Exception {
        Optional<JobOffer> optionalJobOffer = jobOfferRepository.findById(id);
        if(optionalJobOffer.isPresent()){
            return optionalJobOffer.get();
        }else{
            throw new Exception("offer not found");
        }
    }

    public SearchResultJobOffersDto getSearchResultJobOffers(JobOfferCriteriaDto criteriaDto){
        List<JobOffer> jobOffers=findJobOffersWithCriteria(criteriaDto);
        SearchResultJobOffersDto finalResult=new SearchResultJobOffersDto();
        int count =jobOffers.size();
        finalResult.setResultsCount(count);

        List<JobOfferResultDto> jobOfferResults=new ArrayList<>();

        Pagination pagination=criteriaDto.getPagination();
        if(pagination!=null){
            Integer page=pagination.getPageNumber();
            finalResult.setPage(page);
            int offersPerPage=pagination.getItemsPerPage();
            int startOfPage=page-1;
            startOfPage*=offersPerPage;
            int endOfPage=startOfPage+offersPerPage;
            for(int offerNumber=startOfPage;offerNumber<endOfPage&&offerNumber<count;offerNumber++){
                jobOfferResults.add(convertJobOfferEntityToResultDto(jobOffers.get(offerNumber)));
            }
        }else{
            for(JobOffer jobOffer:jobOffers){
                jobOfferResults.add(convertJobOfferEntityToResultDto(jobOffer));
            }

        }

        finalResult.setResults(jobOfferResults);

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
        List<Profession> professionList=criteriaDto.getProfessions();
        if(professionList!=null&&!professionList.isEmpty()){
            List<Predicate>professionPredicates=new ArrayList<>();
            for(Profession profession:professionList){
                Predicate singleProfessionPredicate=criteriaBuilder.equal(jobOfferRoot.get("profession"),profession);
                professionPredicates.add(singleProfessionPredicate);
            }
            Predicate multipleProfessionsPredicate=criteriaBuilder.or(professionPredicates.toArray(new Predicate[]{}));
            predicatesList.add(multipleProfessionsPredicate);
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

    public JobOffer saveOrUpdate(JobOffer jobOffer){
        return jobOfferRepository.save(jobOffer);
    }
}
