package com.mintu.rabbitmqexample.Config;

import com.mintu.rabbitmqexample.Consumers.Consumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    public static final String EXCHANGE = "exchange";
    public static final String REQUEST_QUEUE = "request";
    public static final String REQUEST_ROUTING_KEY = "request_routing_key";
    public static final String EVENTS_QUEUE = "events";
    public static final String EVENTS_ROUTING_KEY = "events_routing_key";

    @Bean
    public Queue queue1() {
        return new Queue(REQUEST_QUEUE);
    }

    @Bean
    public Queue queue2() {
        return new Queue(EVENTS_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bind1(TopicExchange exchange) {
        return BindingBuilder.bind(queue1()).to(exchange).with(REQUEST_ROUTING_KEY);
    }

    @Bean
    public Binding bind2(TopicExchange exchange) {
        return BindingBuilder.bind(queue2()).to(exchange).with(EVENTS_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter());
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer(Consumer consumer) {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setQueueNames(REQUEST_QUEUE, EVENTS_QUEUE);
        listenerContainer.setMessageListener(consumer);
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        listenerContainer.setConcurrency("4");
        listenerContainer.setPrefetchCount(1);
        return listenerContainer;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }
}
