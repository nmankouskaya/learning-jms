package org.learning.service;

import lombok.RequiredArgsConstructor;
import org.learning.config.EventConstants;
import org.learning.event.NotificationEvent;
import org.learning.exception.UnprocessableNotificationException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventReceiver {

    @RabbitListener(queues = EventConstants.QUEUE_CREATE_NOTIFICATION, containerFactory = "simpleRabbitListenerContainerFactory")
    public void receiveWithFiltering(NotificationEvent event) throws UnprocessableNotificationException {
        System.out.println("New notification! Type: " + event.getType() + ", good name: " +
                event.getName() + ", created date: " + event.getCreationDateTime() + ", modified date: " + event.getModifiedDateTime());
        filtering(event);
    }

    private void filtering(NotificationEvent event) throws UnprocessableNotificationException {
        if (event.getName().startsWith("b")) {
            throw new UnprocessableNotificationException("Goods started from 'b' is not allowed: " + event.getName());
        }
    }
}
