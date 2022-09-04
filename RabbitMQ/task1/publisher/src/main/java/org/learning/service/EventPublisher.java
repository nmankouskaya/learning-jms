package org.learning.service;

import org.learning.config.EventConstants;
import org.learning.event.NotificationEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(NotificationEvent event) {
        rabbitTemplate.convertAndSend(EventConstants.EXCHANGE_CREATE_NOTIFICATION, EventConstants.CREATE_NOTIFICATION, event);
        System.out.println("Notification for receipt of good is sent.");
    }
}
