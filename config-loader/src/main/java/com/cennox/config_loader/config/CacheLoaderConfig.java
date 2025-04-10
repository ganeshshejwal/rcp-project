package com.cennox.config_loader.config;

import com.cennox.config_loader.job.CacheLoaderJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheLoaderConfig {

    @Bean
    public JobDetail cacheLoaderJobDetail() {
        return JobBuilder.newJob(CacheLoaderJob.class)
                .withIdentity("cacheLoaderJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger cacheLoaderTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)   
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(cacheLoaderJobDetail())
                .withIdentity("cacheLoaderTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
