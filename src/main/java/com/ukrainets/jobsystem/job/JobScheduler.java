package com.ukrainets.jobsystem.job;

import com.ukrainets.jobsystem.job.enums.JobState;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class JobScheduler {
    private final ThreadPoolTaskScheduler executor;
    private final int maxConcurrentJobs;
    private final List<AbstractJob> jobList = Collections.synchronizedList(new ArrayList<>());
    private final Map<AbstractJob, Future> scheduledList = Collections.synchronizedMap(new HashMap<>());

    public JobScheduler(int maxConcurrentJobs) {
        this.maxConcurrentJobs = maxConcurrentJobs;
        this.executor = threadPoolTaskExecutor(maxConcurrentJobs);
    }

    private ThreadPoolTaskScheduler threadPoolTaskExecutor(int maxConcurrentJobs) {
        var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(maxConcurrentJobs);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "ThreadPoolTaskScheduler");
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }

    public void scheduleOneTimeJob(AbstractJob job) {
        jobList.add(job);
        executor.execute(job);
    }

    public void schedulePeriodicJob(AbstractJob job, int hours) {
        jobList.add(job);

        var scheduledFuture = executor.schedule(job, new PeriodicTrigger(hours, TimeUnit.HOURS));
        scheduledList.put(job, scheduledFuture);
    }

    public void cancelScheduledTask(AbstractJob job) {
        scheduledList.get(job).cancel(true);
    }

    public JobState getJobState(UUID jobId) throws Exception {
        return jobList.stream()
                .filter(j -> jobId.equals(j.getUuid()))
                .findFirst()
                .map(AbstractJob::getState)
                .orElseThrow(Exception::new);
    }

    public List<AbstractJob> getJobList() {
        return this.jobList;
    }

    public int getMaxConcurrentJobs() {
        return this.maxConcurrentJobs;
    }
}
