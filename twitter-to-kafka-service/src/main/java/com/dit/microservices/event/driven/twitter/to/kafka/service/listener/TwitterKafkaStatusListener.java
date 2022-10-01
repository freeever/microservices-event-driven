package com.dit.microservices.event.driven.twitter.to.kafka.service.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Log4j2
@Component
public class TwitterKafkaStatusListener extends StatusAdapter {

    @Override
    public void onStatus(Status status) {
        log.info("Received status text {}", status.getText());
    }
}
