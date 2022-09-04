package org.learning.service;

import lombok.RequiredArgsConstructor;
import org.learning.config.EventConstants;
import org.learning.event.NotificationEvent;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishToFailed(NotificationEvent event) {
        rabbitTemplate.convertAndSend(EventConstants.EXCHANGE_FAILED_NOTIFICATION, EventConstants.FAILED_NOTIFICATION, event);
    }

    public void publishToWait(NotificationEvent event) {
        rabbitTemplate.convertAndSend(EventConstants.EXCHANGE_NOTIFICATION_WAIT, EventConstants.NOTIFICATION_WAIT, event);
    }

    public void publishDLQ(NotificationEvent event) {
        rabbitTemplate.convertAndSend(EventConstants.EXCHANGE_DLQ, EventConstants.DEAD_LETTER_QUEUE, event);
    }
}