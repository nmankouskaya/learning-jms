package org.learning.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class KafkaSimpleProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendMetadata(String metadata) {
        kafkaTemplate.sendDefault(metadata);
        return metadata;
    }

    public String sendMetadata(String topic, String metadata) {
        kafkaTemplate.send(topic, metadata);
        return metadata;
    }
}
