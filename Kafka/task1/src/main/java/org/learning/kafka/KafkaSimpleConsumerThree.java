package org.learning.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.learning.model.TransportMetadata;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static org.learning.kafka.KafkaConstants.BOOTSTRAP_SERVERS;
import static org.learning.kafka.KafkaConstants.CONSUMER_GROUP_ID;
import static org.learning.kafka.KafkaConstants.DESERIALIZER;
import static org.learning.kafka.KafkaConstants.PARSE_TRANSPORT_METADATA;

/*
Kafka guarantees order within a single partition, by the order they arrived at the broker,
not by any other field or by time. All Kafka clients (Consumer, Streams, third-party libraries)
respect this fact. That is why I assign Consumer to a particular partition. Each consumer will
receive messages in the correct order and process them. The minus - rebalancing is not possible
automatically now.
 */
public class KafkaSimpleConsumerThree {

    public static void main(String[] args) {

        //Setup Properties for consumer
        Properties kafkaProps = new Properties();

        //List of Kafka brokers to connect to
        kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        //Deserializer class to convert Keys from Byte Array to String
        kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, DESERIALIZER);

        //Deserializer class to convert Messages from Byte Array to String
        kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DESERIALIZER);

        //Consumer Group ID for this consumer
        kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);

        //Set to consume from the earliest message, on start when no offset is
        //available in Kafka
        kafkaProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        //Create a Consumer
        KafkaConsumer<String, String> simpleConsumer = new KafkaConsumer<>(kafkaProps);

        //Subscribe to the topic
        simpleConsumer.assign(Arrays.asList(new TopicPartition(PARSE_TRANSPORT_METADATA, 2)));


        while (true) {
            //Poll with timeout of 100 milli seconds
            ConsumerRecords<String, String> messages =
                    simpleConsumer.poll(Duration.ofMillis(100));

            //Print batch of records consumed
            for (ConsumerRecord<String, String> message : messages) {
                TransportMetadata metadata = ConsumerUtil.read(message.value());
                System.out.println("Consumer 3: New message received! For car id " +
                        metadata.getId() + " distance is " + ConsumerUtil.process(metadata));
            }
        }
    }
}
