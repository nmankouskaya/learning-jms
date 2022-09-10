package org.learning.kafka;

import org.learning.model.TransportMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Properties;

import static org.learning.kafka.KafkaConstants.*;

@Component
public class KafkaSimpleProducer {

    public void sendToKafka(TransportMetadata transportMetadata) {

        //Setup Properties for Kafka Producer
        Properties kafkaProps = new Properties();

        //List of brokers to connect to
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        //Serializer class used to convert Keys to Byte Arrays
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, SERIALIZER);

        //Serializer class used to convert Messages to Byte Arrays
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SERIALIZER);

        //Create a Kafka producer from configuration
        KafkaProducer simpleProducer = new KafkaProducer(kafkaProps);

        //Publish messages with a random key
        try {

            //Create a producer Record
            ProducerRecord<String, String> kafkaRecord =
                    new ProducerRecord<String, String>(
                            PARSE_TRANSPORT_METADATA,
                            LocalDateTime.now().toString(),
                            write(transportMetadata)
                    );

            System.out.println("Sending Message : " + kafkaRecord);

            //Publish to Kafka -async
            simpleProducer.send(kafkaRecord);
            simpleProducer.flush();
        } catch (Exception e) {

        } finally {
            simpleProducer.close();
        }

    }

    private String write(TransportMetadata data) {
        ObjectMapper mapper= new ObjectMapper();
        String writeData = null;
        try {
            writeData = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return writeData;
    }
}
