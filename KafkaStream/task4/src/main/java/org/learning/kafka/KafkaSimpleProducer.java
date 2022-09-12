package org.learning.kafka;

import org.learning.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class KafkaSimpleProducer {
    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplate;

    public void sendMetadata(Employee metadata) {
        kafkaTemplate.sendDefault(metadata);
    }
}
