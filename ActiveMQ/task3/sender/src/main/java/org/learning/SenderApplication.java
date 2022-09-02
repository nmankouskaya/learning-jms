package org.learning;

import org.learning.sender.Sender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import java.util.Scanner;

@EnableJms
@SpringBootApplication
public class SenderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SenderApplication.class, args);
        run(context);
    }

    private static void run(ConfigurableApplicationContext context ) {
        Sender sender = context.getBean(Sender.class);
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome to the chat. Please, enter the message or Q to close the dialog.");
        while(in.hasNextLine()) {
            String message = in.nextLine();
            if(message.equals("Q")) {
                System.out.println("Closing the dialog.");
                break;
            }
            sender.sendMessage(message);
        }
    }
}
