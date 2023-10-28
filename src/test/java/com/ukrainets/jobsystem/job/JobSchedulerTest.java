package com.ukrainets.jobsystem.job;

import org.junit.jupiter.api.Test;

class JobSchedulerTest {
    private final JobScheduler jobScheduler = new JobScheduler(8);

    @Test
    void scheduleOneTimeJob() {
        jobScheduler.scheduleOneTimeJob(new MyJob());
    }
}