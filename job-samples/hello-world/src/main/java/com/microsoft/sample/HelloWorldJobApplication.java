package com.microsoft.sample;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorldJobApplication implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloWorldJobApplication.class);

    private final static String ARG_NAME_SLEEP = "--sleep=";

    private final static String ENV_PREFIX = "JOB_";
    
    public static void main(String[] args) {
        LOGGER.info("Starting the job ......");
        
        SpringApplication.run(HelloWorldJobApplication.class, args);
        
        LOGGER.info("Job finished.");
    }
    
    @Override
    public void run(String... args) throws Exception {
        Map<String, String> env = System.getenv();
        for(String key : env.keySet()) {
            if(key.startsWith(ENV_PREFIX)) {
                LOGGER.info("Found environment variable {}={}", key, env.get(key));
            }
        }
        
        LOGGER.info("Input arguments are: {}", String.join(" ", args));
        
        int sleep = 30;
        for (String arg : args) {
            if(arg.startsWith(ARG_NAME_SLEEP)) {
                sleep = Integer.parseInt(arg, ARG_NAME_SLEEP.length(), arg.length(), 10);
            }
        }
        
        LOGGER.info("Job will sleep for {} seconds", sleep);
        Thread.sleep(sleep * 1000);
        
    }
    
}
