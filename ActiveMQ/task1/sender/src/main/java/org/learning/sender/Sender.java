package org.learning.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    private static final String ORDER_TOPIC = "order-topic";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(ORDER_TOPIC, message);
    }

}
