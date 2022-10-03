package com.dit.microservices.event.driven.twitter.to.kafka.service.listener;

import com.dit.microservices.event.driven.config.KafkaConfigData;
import com.dit.microservices.event.driven.kafka.avro.model.TwitterAvroModel;
import com.dit.microservices.event.driven.kafka.producer.config.service.KafkaProducer;
import com.dit.microservices.event.driven.twitter.to.kafka.service.transformer.TwitterStatusToAvroTransformer;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Log4j2
@Component
public class TwitterKafkaStatusListener extends StatusAdapter {

    private final KafkaConfigData kafkaConfigData;

    private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;

    private final TwitterStatusToAvroTransformer twitterStatusToAvroTransformer;

    public TwitterKafkaStatusListener(KafkaConfigData configData,
                                      KafkaProducer<Long, TwitterAvroModel> producer,
                                      TwitterStatusToAvroTransformer transformer) {
        this.kafkaConfigData = configData;
        this.kafkaProducer = producer;
        this.twitterStatusToAvroTransformer = transformer;
    }

    @Override
    public void onStatus(Status status) {
        log.info("Received status text {} sending to kafka topic {}", status.getText(), kafkaConfigData.getTopicName());
        TwitterAvroModel twitterAvroModel = twitterStatusToAvroTransformer.getTwitterAvroModelFromStatus(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
    }

}
