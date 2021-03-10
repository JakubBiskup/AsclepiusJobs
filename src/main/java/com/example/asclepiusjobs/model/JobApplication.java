package com.example.asclepiusjobs.model;

import com.example.asclepiusjobs.model.enums.JobApplicationState;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(JobApplicationId.class)
public class JobApplication {

    @Id
    @ManyToOne
    @JoinColumn(name = "job_offer_id", referencedColumnName = "id")
    private JobOffer jobOffer;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Column(name = "text",length = 2000)
    private String text;

    @Column(name = "establishment_comment",length = 2000)
    private String establishmentComment;

    @Column(name = "state", nullable = false)
    private JobApplicationState state;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEstablishmentComment() {
        return establishmentComment;
    }

    public void setEstablishmentComment(String establishmentComment) {
        this.establishmentComment = establishmentComment;
    }

    public JobApplicationState getState() {
        return state;
    }

    public void setState(JobApplicationState state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
