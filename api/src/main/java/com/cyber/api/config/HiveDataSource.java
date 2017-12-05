package com.cyber.api.config;

import org.springframework.boot.autoconfigure.jdbc.TomcatDataSourceConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import org.datanucleus.store.rdbms.datasource.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class HiveDataSource {

    @Value("${spring.hive.url}")
    private String url;

    @Value("${spring.hive.driverClassName}")
    private String DriverClassName;

    @Value("${spring.hive.username}")
    private String username;

    @Value("${spring.hive.password}")
    private String password;

    public DataSource hiveDataSource() {

        BasicDataSource datasource = new BasicDataSource();
        datasource.setUrl(url);
        datasource.setDriverClassName(DriverClassName);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setTestWhileIdle(false);
        datasource.setTestOnBorrow(true);
        datasource.setValidationQuery("show tables");
        datasource.setTestOnReturn(false);
        datasource.setTimeBetweenEvictionRunsMillis(30000);
        datasource.setMaxActive(15);
        datasource.setInitialSize(3);
        datasource.setMaxWait(10000);

        return datasource;
    }

    @Bean
    public JdbcTemplate hiveTemplate() {
        return new JdbcTemplate(hiveDataSource());
    }
    
    @Bean
    public JdbcTemplate hiveTemplateIngest() {
        return new JdbcTemplate(hiveDataSource());
    }

}
