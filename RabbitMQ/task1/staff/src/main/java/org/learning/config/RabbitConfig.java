package org.learning.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@RequiredArgsConstructor
@Configuration
public class RabbitConfig {

    private final ConnectionFactory connectionFactory;

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        var container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        container.setConcurrentConsumers(5);
        container.setMaxConcurrentConsumers(10);
        container.setPrefetchCount(1);
        container.setConsecutiveActiveTrigger(3);
        container.setStartConsumerMinInterval(Duration.ofSeconds(5).toMillis());
        container.setDefaultRequeueRejected(true);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return container;
    }
}