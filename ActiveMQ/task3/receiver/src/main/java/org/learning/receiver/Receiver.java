package org.learning.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private static final String ORDER_TOPIC_1 = "Consumer.consumer1.VirtualTopic.BookOrders";
    private static final String ORDER_TOPIC_2 = "Consumer.consumer2.VirtualTopic.BookOrders";
    private static final String ORDER_TOPIC_3 = "Consumer.consumer3.VirtualTopic.BookOrders";

    @JmsListener(destination = ORDER_TOPIC_1)
    public void receiveFromQueue1(@Payload String message) {
        System.out.println("Message is received by consumer1: " + message);
    }

    @JmsListener(destination = ORDER_TOPIC_2)
    public void receiveFromQueue2(@Payload String message) {
        System.out.println("Message is received by consumer2: " + message);
    }

    @JmsListener(destination = ORDER_TOPIC_3)
    public void receiveFromQueue3(@Payload String message) {
        System.out.println("Message is received by consumer3: " + message);
    }
}
