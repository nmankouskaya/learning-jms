package org.learning.config;

import lombok.RequiredArgsConstructor;
import org.learning.service.SendEventMessageRecoverer;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import java.time.Duration;

@RequiredArgsConstructor
@Configuration
public class RabbitConfig {

    private final ConnectionFactory connectionFactory;
    private final SendEventMessageRecoverer sendEventMessageRecoverer;

    @Value("${org.learning.rabbitmq.listener.custom.retry.backoff-initial-interval:30s}")
    private Duration backOffInitialInterval;
    @Value("${org.learning.rabbitmq.listener.custom.retry.backoff-multiplier:1.5}")
    private Double backOffMultiplier;
    @Value("${org.learning.rabbitmq.listener.custom.retry.backoff-max-interval:1m}")
    private Duration backOffMaxInterval;
    @Value("${org.learning.rabbitmq.listener.custom.retry.max-attempts:2}")
    private Integer retryMaxAttempts;

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        var container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        container.setAdviceChain(retryOperationsInterceptor());
        container.setConcurrentConsumers(5);
        container.setMaxConcurrentConsumers(10);
        container.setPrefetchCount(1);
        container.setConsecutiveActiveTrigger(3);
        container.setStartConsumerMinInterval(Duration.ofSeconds(5).toMillis());
        container.setDefaultRequeueRejected(true);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return container;
    }

    @Bean
    public RetryOperationsInterceptor retryOperationsInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .backOffOptions(backOffInitialInterval.toMillis(), backOffMultiplier, backOffMaxInterval.toMillis())
                .maxAttempts(retryMaxAttempts)
                .recoverer(sendEventMessageRecoverer)
                .build();
    }

}