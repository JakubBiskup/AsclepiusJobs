package com.example.asclepiusjobs.model;

import java.io.Serializable;
import java.util.Objects;

public class SkillOrderOnCvId implements Serializable {

    private long cv;
    private long skill;

    public SkillOrderOnCvId() {
    }

    public SkillOrderOnCvId(long cv, long skill) {
        this.cv = cv;
        this.skill = skill;
    }

    public long getCv() {
        return cv;
    }

    public void setCv(long cv) {
        this.cv = cv;
    }

    public long getSkill() {
        return skill;
    }

    public void setSkill(long skill) {
        this.skill = skill;
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if(!(o instanceof SkillOrderOnCvId)) return false;
        SkillOrderOnCvId that = (SkillOrderOnCvId) o;
        return cv == that.cv && skill== that.cv;

    }

    @Override
    public int hashCode() {
        return Objects.hash(getCv(), getSkill());
    }
}
