package org.learning.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.learning.dto.CustomSerdes;
import org.learning.dto.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.time.Duration;
import java.util.*;

@Slf4j
@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamConfig {

    @Value("${kafka.topic.topic4}")
    private String topic4;
    /**
     * TASK 4
     * The Stream which delegates each incoming topic to respective destination topic
     * @param kStreamsBuilder
     * @return
     */
    @Bean
    public KStream<String, Employee> kStreamTask1(StreamsBuilder kStreamsBuilder){
        KStream<String, Employee> stream = kStreamsBuilder.stream(topic4);
        stream.peek((key, message) -> log.info("Received message: " + message));
        return stream;
    }

}
