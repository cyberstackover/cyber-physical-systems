package com.cyber.api.config;

import com.cyber.api.job.DataCollectorWorker;
import java.util.concurrent.CountDownLatch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Configuration
public class RedisMessagingConfig {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("datacollector"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(DataCollectorWorker receiver) {
        return new MessageListenerAdapter(receiver, "receiveData");
    }

    @Bean
    DataCollectorWorker dataCollectorWorker(CountDownLatch latch) {
        return new DataCollectorWorker(latch);
    }

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
