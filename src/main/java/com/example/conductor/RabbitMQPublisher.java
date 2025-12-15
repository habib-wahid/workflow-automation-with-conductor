package com.example.conductor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQPublisher.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessage(MessageEvent messageEvent) {
        logger.info("Publishing message to RabbitMQ: {}", messageEvent);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, messageEvent);
        logger.info("Message published successfully");
    }

    public void publishMessage(String exchange, String routingKey, Object message) {
        logger.info("Publishing message to exchange: {}, routingKey: {}, message: {}",
                    exchange, routingKey, message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        logger.info("Message published successfully");
    }
}

