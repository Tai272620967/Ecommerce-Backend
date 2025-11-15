package vn.hoidanit.jobhunter.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Configuration class for JMS (Java Message Service) to connect with AmazonMQ
 * AmazonMQ supports ActiveMQ protocol, so we use ActiveMQConnectionFactory
 */
@Configuration
@EnableJms
public class JmsConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user:}")
    private String username;

    @Value("${spring.activemq.password:}")
    private String password;

    /**
     * Create ActiveMQ ConnectionFactory to connect with AmazonMQ broker
     * AmazonMQ provides broker URL in format: ssl://broker-id.mq.ap-southeast-1.amazonaws.com:61617
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        
        // Set username and password if provided (AmazonMQ requires authentication)
        if (username != null && !username.isEmpty()) {
            connectionFactory.setUserName(username);
        }
        if (password != null && !password.isEmpty()) {
            connectionFactory.setPassword(password);
        }
        
        // Configure for AmazonMQ SSL connection
        connectionFactory.setTrustAllPackages(true);
        
        return connectionFactory;
    }

    /**
     * Message converter to serialize/deserialize Java objects to JSON
     * This allows sending complex objects as messages
     */
    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    /**
     * JmsTemplate is used for sending messages to queues/topics
     */
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setMessageConverter(messageConverter);
        // Enable explicit QoS (Quality of Service)
        template.setDeliveryPersistent(true);
        return template;
    }

    /**
     * JmsListenerContainerFactory for receiving messages from queues/topics
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        // Enable concurrent consumers for better performance
        factory.setConcurrency("1-10");
        // Set acknowledge mode to auto
        factory.setSessionAcknowledgeMode(jakarta.jms.Session.AUTO_ACKNOWLEDGE);
        return factory;
    }
}

