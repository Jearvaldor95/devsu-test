package com.devsu.customer_service.infraestruture.adapter.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AccountRabbitMQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AccountRabbitMQConsumer.class);


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
        return factory;
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleAccountCreated(AccountCreatedEvent event) {
        logger.info("Account created: Customer ID = " + event.getCustomerId() +
                ", AccountNumber = " + event.getAccountNumber() +
                ", AccountType = " + event.getAccountType());


    }
}
