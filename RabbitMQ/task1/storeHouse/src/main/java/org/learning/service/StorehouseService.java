package org.learning.service;

import org.learning.dto.BookOrder;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class StorehouseService {
    private static final String ORDER_CREATED_QUEUE = "order.created.queue";
    private static final String ORDER_PROCESSED_QUEUE = "order.processed.queue";

    @JmsListener(destination = ORDER_CREATED_QUEUE)
    @SendTo(ORDER_PROCESSED_QUEUE)
    public Message<BookOrder> receive(@Payload BookOrder bookOrder) {
        System.out.println("Order is received: " + bookOrder.getBookTitle());
        bookOrder.setStatus("PROCESSED");
        return MessageBuilder.withPayload(bookOrder).setHeader("status", bookOrder.getStatus()).build();
    }
}
