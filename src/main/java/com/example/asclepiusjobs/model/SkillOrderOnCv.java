package com.example.asclepiusjobs.model;


import javax.persistence.*;

@Entity
@IdClass(SkillOrderOnCvId.class)
@Table(name="skill_order_on_cv",
uniqueConstraints = @UniqueConstraint(columnNames = {"cv_id","appearance_order"}))
public class SkillOrderOnCv {

    @Id
    @ManyToOne
    @JoinColumn(name = "cv_id")
    private Cv cv;

    @Id
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Column(name = "appearance_order")
    private Integer appearanceOrder;

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Integer getAppearanceOrder() {
        return appearanceOrder;
    }

    public void setAppearanceOrder(Integer appearanceOrder) {
        this.appearanceOrder = appearanceOrder;
    }
}
