package org.learning;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.learning.dto.BookOrder;
import org.learning.service.BookshopService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

@EnableTransactionManagement
@EnableJms
@SpringBootApplication
public class BookShopApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BookShopApplication.class, args);
        runBookStore(context);
    }

    private static void runBookStore(ConfigurableApplicationContext context ) {
        BookshopService bookshopService = context.getBean(BookshopService.class);
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the book store. Please, enter the book title to order or Q to close the dialog.");
        while(in.hasNextLine()) {
            String title = in.nextLine();
            if(title.equals("Q")) {
                System.out.println("Closing the dialog.");
                break;
            }
            bookshopService.sendOrder(new BookOrder(title, LocalDateTime.now(), "CREATED"));
        }
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public JmsTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory) {
        JmsTransactionManager transactionManager = new JmsTransactionManager();
        transactionManager.setRollbackOnCommitFailure(true);
        transactionManager.setNestedTransactionAllowed(true);
        transactionManager.setFailEarlyOnGlobalRollbackOnly(true);
        transactionManager.setTransactionSynchronization(0);
        transactionManager.setConnectionFactory(connectionFactory);
        return transactionManager;
    }
}
