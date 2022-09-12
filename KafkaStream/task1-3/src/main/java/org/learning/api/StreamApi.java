package org.learning.api;

import lombok.extern.slf4j.Slf4j;
import org.learning.kafka.KafkaSimpleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("kafka-streams")
public class StreamApi {

    @Value("${kafka.topic.topic2}")
    private String topic2;

    @Autowired
    private KafkaSimpleProducer simpleProducer;

    @PostMapping("/task1")
    public String processTask1(@RequestBody String data) {
        simpleProducer.sendMetadata(data);
        log.info("Message was sent");
        return "Request is sent: " + data;
    }

    @PostMapping("/task2")
    public String processTask2(@RequestBody String data) {
        simpleProducer.sendMetadata(topic2, data);
        log.info("Message was sent");
        return "Request is sent: " + data;
    }

}
