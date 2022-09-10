package org.learning.kafka;

public final class KafkaConstants {

    public static final String PARSE_TRANSPORT_METADATA = "parse-transport-metadata";

    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
    public static final String DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";

    public static final String CONSUMER_GROUP_ID = "group-id";

    private KafkaConstants(){}
}
