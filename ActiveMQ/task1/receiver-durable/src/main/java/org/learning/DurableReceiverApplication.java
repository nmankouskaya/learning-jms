package org.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@EnableJms
@SpringBootApplication
public class DurableReceiverApplication {

    public static void main(String[] args) {
        SpringApplication.run(DurableReceiverApplication.class, args);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(SingleConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        connectionFactory.setClientId("brokerClientId");
        factory.setConnectionFactory(connectionFactory);
        factory.setDestinationResolver(destinationResolver());
        factory.setConcurrency("1");
        factory.setSubscriptionDurable(true);
        return factory;
    }

    @Bean
    public DestinationResolver destinationResolver() {
        return new DynamicDestinationResolver();
    }
}
