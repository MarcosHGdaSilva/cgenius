package br.com.fiap.cgenius.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String CHAT_QUEUE = "chat-queue";

    @Bean
    public Queue queue() {
        return new Queue("email-queue", true);
    }

}
