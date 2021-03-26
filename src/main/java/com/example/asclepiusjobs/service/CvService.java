package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.CvDto;
import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.Skill;
import com.example.asclepiusjobs.model.SkillOrderOnCv;
import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.repository.CvRepository;
import com.example.asclepiusjobs.repository.SkillOrderOnCvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CvService {

    @Autowired
    private CvRepository cvRepository;

    @Autowired
    private SkillOrderOnCvRepository skillOrderOnCvRepository;

    @Autowired
    private UserService userService;



    public Cv createBlankInvisibleCv(User user){
        Cv cv=new Cv();
        cv.setUser(user);
        cv.setVisibility(false);
        Cv savedCv= cvRepository.save(cv);
        user.setCv(cv);
        userService.saveOrUpdate(user);
        return savedCv;
    }

    public Cv makeCvVisible(Long id) throws Exception {
        Cv cv=getCvById(id);
        cv.setVisibility(true);
        return saveOrUpdate(cv);
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
