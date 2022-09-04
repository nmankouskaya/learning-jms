package org.learning.service;

import org.learning.config.EventConstants;
import org.learning.datastore.Datastore;
import org.learning.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventReceiver {
    @Autowired
    private Datastore datastore;

    @RabbitListener(queues = EventConstants.QUEUE_FAILED_NOTIFICATION, containerFactory = "simpleRabbitListenerContainerFactory")
    public void receiveWithFiltering(NotificationEvent event) {
        System.out.println("Received failed notification! Type: " + event.getType() + ", good name: " +
                event.getName() + ", creation date time: " + event.getCreationDateTime() +
                ", modified date time: " + event.getModifiedDateTime());
        datastore.addToStore(event);
    }

    @RabbitListener(queues = EventConstants.DEAD_LETTER_QUEUE, containerFactory = "simpleRabbitListenerContainerFactory")
    public void receiveFromDLQ(NotificationEvent event) {
        System.out.println("Received notifications from DLQ! Type: " + event.getType() + ", good name: " +
                event.getName() + ", creation date time: " + event.getCreationDateTime() +
                ", modified date time: " + event.getModifiedDateTime());
        datastore.addToStore(event);
    }
}
