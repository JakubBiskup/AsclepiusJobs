package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.model.Skill;
import com.example.asclepiusjobs.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Skill createSkill(String name){
        Skill skill=new Skill();
        skill.setName(name);
        return skillRepository.save(skill);
    }

    public Skill getByNameOrReturnNull(String name){
        Optional<Skill> optionalSkill=skillRepository.findByName(name);
        return optionalSkill.orElse(null);
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
