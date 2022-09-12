package org.learning.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamConfig {

    @Value("${kafka.topic.topic11}")
    private String topic11;

    @Value("${kafka.topic.topic12}")
    private String topic12;

    @Value("${kafka.topic.topic2}")
    private String topic2;

    @Value("${kafka.topic.topic31}")
    private String topic31;

    @Value("${kafka.topic.topic32}")
    private String topic32;
    /**
     * TASK 1
     * The Stream which delegates each incoming topic to respective destination topic
     * @param kStreamsBuilder
     * @return
     */
    @Bean
    public KStream<String, String> kStreamTask1(StreamsBuilder kStreamsBuilder){
        KStream<String, String> stream = kStreamsBuilder.stream(topic11);
        stream.peek((key, message) -> log.info("Transferring message: " + message)).to(topic12);
        return stream;
    }

    /**
     * TASK 2
     * The Stream which gets messages from topic, filter them, split in 2 branches depending on key value and finally merge them
     * @param kStreamsBuilder
     * @return
     */
    @Bean
    public KStream<String, String> kStreamTask2(StreamsBuilder kStreamsBuilder) {
        KStream<String, String> stream = kStreamsBuilder.stream(topic2);
        Map<String, KStream<String, String>> branches = stream
                .filter((key, value) -> Objects.nonNull(value))
                .map((key, value) -> {
                    log.info("Message received: " + value);
                    String[] splittedMessage = value.trim().split(" ");
                    return new KeyValue<String, List<String>>(null, Arrays.asList(splittedMessage));
                })
                .flatMap((key, value) -> {
                    List<KeyValue<String, String>> list = new ArrayList<>();
                    value.forEach(v -> list.add(new KeyValue<>(String.valueOf(v.length()), v)));
                    return list;
                })
                .peek((key, value) -> log.info("Split message: <" + key + ", " + value + ">"))
                .split(Named.as("split-"))
                .branch((key, value) -> Integer.valueOf(key) >= 10, Branched.as("words-long"))
                .defaultBranch(Branched.as("words-short"));

        if (branches.get("words-long") != null && branches.get("words-short") != null) {
            KStream<String, String> merged = branches.get("words-long")
                    .filter((key, value) -> !value.contains("a"))
                    .merge(branches.get("words-short").filter((key, value) -> !value.contains("a")))
                    .peek((key, value) -> log.info("Merged message: <" + key + ", " + value + ">"));
        }

        return stream;
    }

    /**
     * TASK 3
     * Read data from two topics and then JOIN them with each other based on our Long key.
     * Data in two topics is in the format: “number:text”. The number is used as the Key to join messages from two topics
     * @param kStreamsBuilder
     * @return
     */
    @Bean
    public KStream<String, String> kStreamTask3(StreamsBuilder kStreamsBuilder) {
        KStream<String, String> stream1 = kStreamsBuilder.stream(topic31);
        KStream<String, String> stream2 = kStreamsBuilder.stream(topic32);

        KStream<String, String> joined = prepareStreamForTask3(stream1)
                .join(
                        prepareStreamForTask3(stream2),
                        (value1, value2) -> value1 + " + + " + value2,
                        JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(30)))
                .peek((key, value) -> log.info("Merged message: <" + key + ", " + value + ">"));

        return joined;
    }

    private KStream<String, String> prepareStreamForTask3(KStream<String, String> stream) {
        return stream
                .filter((key, value) -> Objects.nonNull(value) && value.contains(":"))
                .map((key, value) -> {
                    log.info("Message received: <" + key + ", " + value + "> with timestamp ");
                    String[] splittedMessage = value.trim().split(":");
                    if (splittedMessage.length < 1) {
                        throw new RuntimeException("Incorrect input value");
                    }
                    return new KeyValue<>(splittedMessage[0], splittedMessage[1]);
                })
                .peek((key, value) -> log.info("Changed message: <" + key + ", " + value + ">"));
    }

}
