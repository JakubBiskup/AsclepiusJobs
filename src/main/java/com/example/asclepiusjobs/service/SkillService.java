package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.Skill;
import com.example.asclepiusjobs.model.SkillOrderOnCv;
import com.example.asclepiusjobs.repository.SkillOrderOnCvRepository;
import com.example.asclepiusjobs.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillOrderOnCvRepository orderOnCvRepository;

    public Skill createSkill(String name){
        Skill skill=new Skill();
        skill.setName(name);
        return skillRepository.save(skill);
    }

    public Skill getByNameOrReturnNull(String name){
        Optional<Skill> optionalSkill=skillRepository.findByName(name);
        return optionalSkill.orElse(null);
    }

    public void clearSkillsAndTheirOrderOnCv(Cv cv){
        orderOnCvRepository.deleteByCv(cv);
    }

    //
    public List<Skill> getSkillsInOrderOnCv(Cv cv){
        List<Skill> skillsList=new ArrayList<>();
        List<SkillOrderOnCv> orderList=orderOnCvRepository.findAllByCvOrderByAppearanceOrder(cv);
        for(SkillOrderOnCv orderSkill:orderList){
            skillsList.add(orderSkill.getSkill());
        }
        return skillsList;
    }

    public Skill getById(Long id) throws Exception {
        Optional<Skill> optionalSkill=skillRepository.findById(id);
        if(optionalSkill.isPresent()){
            return optionalSkill.get();
        }else {
            throw new Exception("Skill not found.");
        }
    }

    public void deleteById(Long id){
        skillRepository.deleteById(id);
    }
}
