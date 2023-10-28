package com.ukrainets.jobsystem.job;

import com.ukrainets.jobsystem.job.enums.JobState;

public class MyJob extends AbstractJob {
    @Override
    public void run() {
        System.out.println("Custom Job is running.");
        // Simulate some work
        try {
            this.setState(JobState.RUNNING);
            Thread.sleep(2000);
            System.out.println("Custom Job finished.");
            this.setState(JobState.COMPLETED);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
