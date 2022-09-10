package org.learning.api;

import org.learning.kafka.KafkaSimpleProducer;
import org.learning.model.TransportMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("taxi")
public class TaxiApi {

    @Autowired
    private KafkaSimpleProducer simpleProducer;

    @PostMapping("/process")
    public String processDisctance(@RequestBody TransportMetadata metadata) {
        simpleProducer.sendToKafka(metadata);
        return "Request is send";
    }
}
