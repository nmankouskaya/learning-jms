package org.learning;

import org.learning.event.EventStatus;
import org.learning.event.NotificationEvent;
import org.learning.service.EventPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.util.Scanner;

@SpringBootApplication
public class PublisherApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PublisherApplication.class, args);
        runNotificationSender(context);
    }

    private static void runNotificationSender(ConfigurableApplicationContext context) {
        EventPublisher eventPublisher = context.getBean(EventPublisher.class);
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome!\nPlease, enter the title of the good or Q to close the dialog.");
        while(in.hasNextLine()) {
            String message = in.nextLine();
            if(message.equals("Q")) {
                System.out.println("Closing the dialog.");
                break;
            }
            eventPublisher.publish(createNotification(message));
        }
    }

    private static NotificationEvent createNotification(String message) {
        return NotificationEvent.builder().type(EventStatus.CREATED.name()).name(message).creationDateTime(LocalDateTime.now()).build();
    }
}
