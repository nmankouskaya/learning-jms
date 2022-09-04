package org.learning;

import org.learning.datastore.Datastore;
import org.learning.event.EventStatus;
import org.learning.event.NotificationEvent;
import org.learning.service.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class StaffApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StaffApplication.class, args);
        runStaff(context);
    }

    private static void runStaff(ConfigurableApplicationContext context) {
        EventPublisher eventPublisher = context.getBean(EventPublisher.class);
        Datastore datastore = context.getBean(Datastore.class);
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Staff App!\nPlease, enter R to read notifications from datasource or Q to close the dialog.");
        while(in.hasNextLine()) {
            String message = in.nextLine();
            if(message.equals("Q")) {
                System.out.println("Closing the dialog.");
                break;
            }
            if(message.equals("R")) {
                List<NotificationEvent> allNotifications = datastore.getAllRecords();
                if (!allNotifications.isEmpty()) {
                    allNotifications.stream().forEach(n ->
                            System.out.println(allNotifications.indexOf(n) + ". Name: " + n.getName() + ", type: " + n.getType() +
                                    ", creation date: " + n.getCreationDateTime() + ", modified date: " + n.getModifiedDateTime() + ";\n"));
                    System.out.println("Enter number of the record to modify and new status (RETURN or ARCHIVE) to set separating by space like '1 RETURN' ");
                    message = in.nextLine();
                    if (message.equals("Q")) {
                        System.out.println("Closing the dialog.");
                        break;
                    }
                    String[] splitResult = message.split(" ");
                    if (splitResult.length == 2) {
                        Integer recordNumber;
                        try {
                            recordNumber = Integer.valueOf(splitResult[0]);
                        } catch (NumberFormatException ex) {
                            System.out.println("Incorrect format");
                            continue;
                        }
                        Optional.ofNullable(recordNumber).ifPresent(id -> {
                            if (splitResult[1].equalsIgnoreCase(EventStatus.RETURN.name())) {
                                allNotifications.get(id).setType(splitResult[1]);
                                eventPublisher.publish(allNotifications.get(id));
                                allNotifications.remove(id);
                            } else if (splitResult[1].equalsIgnoreCase(EventStatus.ARCHIVE.name())) {
                                allNotifications.get(id).setType(splitResult[1]);
                            } else {
                                System.out.println("Incorrect format");
                            }
                        });
                    }
                }
            }
        }
    }
}
