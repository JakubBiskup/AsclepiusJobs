package com.example.asclepiusjobs.model;

import java.io.Serializable;
import java.util.Objects;

public class JobApplicationId implements Serializable {

    private long user;
    private long jobOffer;

    public JobApplicationId() {
    }

    public JobApplicationId(long user, long jobOffer) {
        this.user = user;
        this.jobOffer = jobOffer;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(long jobOffer) {
        this.jobOffer = jobOffer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobApplicationId)) return false;
        JobApplicationId that = (JobApplicationId) o;
        return user == that.user && jobOffer == that.jobOffer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, jobOffer);
    }
}
