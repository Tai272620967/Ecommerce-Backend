package vn.hoidanit.jobhunter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * Service for sending and receiving messages via AmazonMQ
 * This service handles message queue operations
 */
@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${amazonmq.queue.name:jobhunter-queue}")
    private String queueName;

    /**
     * Send a text message to the queue
     * 
     * @param message The message content to send
     */
    public void sendMessage(String message) {
        try {
            logger.info("Sending message to queue {}: {}", queueName, message);
            jmsTemplate.convertAndSend(queueName, message);
            logger.info("Message sent successfully to queue: {}", queueName);
        } catch (Exception e) {
            logger.error("Error sending message to queue: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send message", e);
        }
    }

    /**
     * Send an object message to the queue
     * The object will be serialized to JSON automatically
     * 
     * @param payload The object to send
     */
    public void sendObjectMessage(Object payload) {
        try {
            logger.info("Sending object message to queue {}: {}", queueName, payload);
            jmsTemplate.convertAndSend(queueName, payload);
            logger.info("Object message sent successfully to queue: {}", queueName);
        } catch (Exception e) {
            logger.error("Error sending object message to queue: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send object message", e);
        }
    }

    /**
     * Listen for messages from the queue
     * This method will be automatically invoked when a message arrives
     * 
     * @param message The received message
     */
    @JmsListener(destination = "${amazonmq.queue.name:jobhunter-queue}")
    public void receiveMessage(String message) {
        logger.info("Received message from queue {}: {}", queueName, message);
        // Process the message here
        processMessage(message);
    }

    /**
     * Listen for object messages from the queue
     * This method handles complex objects sent as messages
     * 
     * @param payload The received object
     */
    @JmsListener(destination = "${amazonmq.queue.name:jobhunter-queue}")
    public void receiveObjectMessage(Object payload) {
        logger.info("Received object message from queue {}: {}", queueName, payload);
        // Process the object message here
        processObjectMessage(payload);
    }

    /**
     * Process the received text message
     * Override this method or add custom logic as needed
     * 
     * @param message The message to process
     */
    private void processMessage(String message) {
        // Add your business logic here
        logger.info("Processing message: {}", message);
        // Example: Send email notification, update database, etc.
    }

    /**
     * Process the received object message
     * Override this method or add custom logic as needed
     * 
     * @param payload The object to process
     */
    private void processObjectMessage(Object payload) {
        // Add your business logic here
        logger.info("Processing object message: {}", payload);
        // Example: Process order, update inventory, etc.
    }
}

