package com.example.conductor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    //@RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeMessage(MessageEvent messageEvent) {
        logger.info("Received message from RabbitMQ: {}", messageEvent);
        logger.info("Processing message with ID: {}", messageEvent.getMessageId());
        logger.info("Message content: {}", messageEvent.getContent());
        logger.info("Message sender: {}", messageEvent.getSender());
        logger.info("Message timestamp: {}", messageEvent.getTimestamp());

        // Add your business logic here to process the message
        processMessage(messageEvent);
    }

    private void processMessage(MessageEvent messageEvent) {
        // Simulate message processing
        logger.info("Message processing completed for ID: {}", messageEvent.getMessageId());
    }
}

