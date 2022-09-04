package org.learning.service;

import lombok.RequiredArgsConstructor;
import org.learning.config.EventConstants;
import org.learning.event.NotificationEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(NotificationEvent event) {
        rabbitTemplate.convertAndSend(EventConstants.EXCHANGE_CREATE_NOTIFICATION,
                EventConstants.CREATE_NOTIFICATION, event);
    }
}