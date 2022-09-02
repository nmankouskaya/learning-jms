package org.learning.service;

import org.learning.dto.BookOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class BookshopService {
    private static final String ORDER_CREATED_QUEUE = "order.created.queue";
    private static final String ORDER_PROCESSED_QUEUE = "order.processed.queue";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public void sendOrder(BookOrder bookOrder) {
        jmsTemplate.convertAndSend(ORDER_CREATED_QUEUE, bookOrder, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("status", bookOrder.getStatus());
                return message;
            }
        });
        System.out.println("The order for book '" + bookOrder.getBookTitle() + "' was sent.");
    }

    @JmsListener(destination = ORDER_PROCESSED_QUEUE)
    public void receive(@Payload BookOrder bookOrder) {
        System.out.println("Book order for " + bookOrder.getBookTitle() + " is processed with status " +
                bookOrder.getStatus());
    }



}
