package org.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJms
@SpringBootApplication
public class DurableReceiverApplication {

    public static void main(String[] args) {
        SpringApplication.run(DurableReceiverApplication.class, args);
    }
}
