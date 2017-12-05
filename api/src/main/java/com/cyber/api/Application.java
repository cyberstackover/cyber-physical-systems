package com.cyber.api;

import com.cyber.api.config.MethodSecurityConfig;
import com.cyber.api.config.OAuth2ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, /*securedEnabled = true, */ proxyTargetClass = true)
@Import({OAuth2ResourceConfig.class, MethodSecurityConfig.class})
@ComponentScan(basePackages = {"com.cyber.api.*"})
@EnableScheduling
@EnableAsync
public class Application { //extends SpringBootServletInitializer {

//    @Override
 //   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
  //      return application.sources(Application.class);
  //  }

    public static void main(String[] args) throws Exception {

        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}
