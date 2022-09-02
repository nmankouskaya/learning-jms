package org.learning.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private static final String ORDER_TOPIC = "order-topic";

    @JmsListener(destination = ORDER_TOPIC)
    public void receive(@Payload String message) {
        System.out.println("Message is received: " + message);
    }
}
