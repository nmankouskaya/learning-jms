package org.learning.api;

import org.learning.dto.Employee;
import org.learning.kafka.KafkaSimpleProducer;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private KafkaSimpleProducer simpleProducer;

    @PostMapping("/task4")
    public String processTask4(@RequestBody Employee data) {
        simpleProducer.sendMetadata(data);
        log.info("Message was sent");
        return "Request is sent: " + data;
    }

}
