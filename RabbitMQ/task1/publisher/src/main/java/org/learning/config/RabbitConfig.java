package org.learning.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class RabbitConfig {
    private AmqpAdmin amqpAdmin;
    private int waitingTime = 6000;

    public RabbitConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    /**
     * Composes Bindings between Exchange and Queue's
     */
    @PostConstruct
    public void configureQueuesExchangesAndBindings() {
        // DLQ
        Exchange dqlExchange = new DirectExchange(EventConstants.EXCHANGE_DLQ);
        amqpAdmin.declareExchange(dqlExchange);
        Queue deadLetterQueue = new Queue(EventConstants.DEAD_LETTER_QUEUE);
        amqpAdmin.declareQueue(deadLetterQueue);
        amqpAdmin.declareBinding(BindingBuilder
                .bind(deadLetterQueue)
                .to(dqlExchange)
                .with(EventConstants.DEAD_LETTER_QUEUE)
                .and(null));

        // CREATE NOTIFICATION
        Exchange createNotificationExchange = new DirectExchange(EventConstants.EXCHANGE_CREATE_NOTIFICATION);
        amqpAdmin.declareExchange(createNotificationExchange);
        Queue createNotificationQueue = QueueBuilder
                .durable(EventConstants.QUEUE_CREATE_NOTIFICATION)
                .deadLetterExchange(EventConstants.EXCHANGE_DLQ)
                .deadLetterRoutingKey(EventConstants.DEAD_LETTER_QUEUE)
                .ttl(waitingTime) //if ttl expires then move from create to dlq
                .build();
        amqpAdmin.declareQueue(createNotificationQueue);
        amqpAdmin.declareBinding(BindingBuilder
                .bind(createNotificationQueue)
                .to(createNotificationExchange)
                .with(EventConstants.CREATE_NOTIFICATION)
                .and(Map.of("x-max-length", 5)));

        // NOTIFICATION WAIT
        Exchange notificationWait = new DirectExchange(EventConstants.EXCHANGE_NOTIFICATION_WAIT);
        amqpAdmin.declareExchange(notificationWait);
        Queue notificationWaitQueue = QueueBuilder
                        .durable(EventConstants.QUEUE_NOTIFICATION_WAIT)
                        .deadLetterExchange(EventConstants.EXCHANGE_CREATE_NOTIFICATION)
                        .deadLetterRoutingKey(EventConstants.CREATE_NOTIFICATION)
                        .ttl(waitingTime) //if ttl expires then move from waiting queue to initial create queue
                        .build();
        amqpAdmin.declareQueue(notificationWaitQueue);
        amqpAdmin.declareBinding(BindingBuilder
                .bind(notificationWaitQueue)
                .to(notificationWait)
                .with(EventConstants.NOTIFICATION_RETRY)
                .and(null));

        // NOTIFICATION FAILED
        Exchange notificationFailed = new DirectExchange(EventConstants.EXCHANGE_FAILED_NOTIFICATION);
        amqpAdmin.declareExchange(notificationFailed);
        Queue notificationFailedQueue = QueueBuilder
                .durable(EventConstants.QUEUE_FAILED_NOTIFICATION)
                .build();
        amqpAdmin.declareQueue(notificationFailedQueue);
        amqpAdmin.declareBinding(BindingBuilder
                .bind(notificationFailedQueue)
                .to(notificationFailed)
                .with(EventConstants.FAILED_NOTIFICATION)
                .and(null));
    }
}

