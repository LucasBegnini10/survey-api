package com.survey.server.infrastructure.queue.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnswerPublisherConfig {

    @Value("${queue.answer.name}")
    private String message;

    @Value("${queue.answer.exchange}")
    private String exchange;

    @Value("${queue.answer.routing-key}")
    private String routingKey;

    @Bean
    public Queue queue() {
        return new Queue(message, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding testeBinding(Queue testeQueue, DirectExchange exchange) {
        return BindingBuilder.bind(testeQueue).to(exchange).with(routingKey);
    }

}
