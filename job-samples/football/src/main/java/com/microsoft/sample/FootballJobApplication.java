package com.microsoft.sample;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.batch.samples.football.FootballJobConfiguration;

public class FootballJobApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(FootballJobApplication.class);
    
    public static void main(String[] args) throws Exception {

        try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(FootballJobConfiguration.class)) {
            JobLauncher jobLauncher = context.getBean(JobLauncher.class);
            Job job = context.getBean(Job.class);
            DataSource ds = context.getBean(DataSource.class);
            int count = JdbcClient.create(new JdbcTemplate(ds))
                    .sql("SELECT COUNT(0) FROM PLAYER_SUMMARY")
                    .query(Integer.class)
                    .single();
            LOGGER.info("There is {} player summary before job execution");

            jobLauncher.run(job, new JobParameters());
            
            count = JdbcClient.create(new JdbcTemplate(ds))
                    .sql("SELECT COUNT(0) FROM PLAYER_SUMMARY")
                    .query(Integer.class)
                    .single();
            LOGGER.info("There is {} player summary after job execution", count);
        }
    }

}
