package com.cyber.sensor;

import com.cyber.Matagaruda;
import com.cyber.authentication.GrantType;
import com.cyber.exceptions.MatagarudaException;
import com.cyber.exceptions.MatagarudaOAuthException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class Application implements SchedulingConfigurer {

    @SuppressWarnings({"unused", "resource"})
    public static void main(String args[]) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
    }

    @Bean
    public JobWriter bean() {
        return new JobWriter();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }

    @Bean
    public Matagaruda matagaruda() throws MatagarudaException, MatagarudaOAuthException {
        String[] scopes = {"insert", "select", "update", "delete"};
        Matagaruda client = new Matagaruda()
                .setClientId("9f0a81ff533adecc345588765c63c1c0")
                .setClientSecret("b9c006271cb9c0a3eb4c7bb0fadcedcd")
                .setScopes(scopes)
                .setGrantType(GrantType.CLIENT_CREDENTIALS)
                .build();
        return client;
    }
}
