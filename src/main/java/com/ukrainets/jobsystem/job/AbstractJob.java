package com.ukrainets.jobsystem.job;

import com.ukrainets.jobsystem.job.enums.JobState;
import com.ukrainets.jobsystem.job.enums.JobType;

import java.util.UUID;

public abstract class AbstractJob implements Runnable {

    private UUID uuid;
    private JobType jobType;
    private JobState state;

    public AbstractJob() {
    }

    public AbstractJob(JobType jobType) {
        this.uuid = UUID.randomUUID();
        this.jobType = jobType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public JobState getState() {
        return state;
    }

    public void setState(JobState jobState) {
        this.state = jobState;
    }
}
