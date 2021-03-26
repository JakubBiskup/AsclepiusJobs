package com.example.asclepiusjobs.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
public class Skill {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "skill")              //relation with SkillOrderOnCv
    private List<SkillOrderOnCv> skillCv;


    @ManyToMany(mappedBy = "skillsRequired")
    private Set<JobOffer> jobOffers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public void setJobOffers(Set<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
    }

    public List<SkillOrderOnCv> getSkillCv() {
        return skillCv;
    }

    public void setSkillCv(List<SkillOrderOnCv> skillCv) {
        this.skillCv = skillCv;
    }
}
