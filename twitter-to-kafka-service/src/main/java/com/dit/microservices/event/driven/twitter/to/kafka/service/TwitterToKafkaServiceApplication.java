package com.dit.microservices.event.driven.twitter.to.kafka.service;

import com.dit.microservices.event.driven.twitter.to.kafka.service.init.StreamInitializer;
import com.dit.microservices.event.driven.twitter.to.kafka.service.runner.StreamRunner;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Log4j2
@SpringBootApplication
@ComponentScan(basePackages = "com.dit.microservices.event.driven")
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private final StreamRunner streamRunner;

    private final StreamInitializer streamInitializer;


    public TwitterToKafkaServiceApplication(StreamRunner runner, StreamInitializer initializer) {
        this.streamRunner = runner;
        this.streamInitializer = initializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("App starts...");
        streamInitializer.init();
        streamRunner.start();
    }
}