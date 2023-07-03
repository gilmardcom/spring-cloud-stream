package com.gilmard.microservices.ask.me;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class AskMeScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(AskMeScheduler.class);
    private final Integer threadPoolSize;
    private final Integer taskQueueSize;

    @Autowired
    public AskMeScheduler(
            @Value("${app.threadPoolSize:8}") Integer threadPoolSize,
            @Value("${app.taskQueueSize:64}") Integer taskQueueSize
    ) {
        this.threadPoolSize = threadPoolSize;
        this.taskQueueSize = taskQueueSize;
    }

    @Bean
    public Scheduler getAskMeScheduler() {
        LOG.info("AskMeScheduler created with poolSize={}", threadPoolSize);
        return Schedulers.newBoundedElastic(threadPoolSize, taskQueueSize, "ask-me-pool");
    }
}
