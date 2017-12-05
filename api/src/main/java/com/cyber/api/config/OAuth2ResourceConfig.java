package com.cyber.api.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableResourceServer
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
        resources.tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/v1/**").permitAll()
                .antMatchers("/v1/agama/search", "/v1/pekerjaan/search", "/v1/propinsi", "/v1/kabupaten/search", "/v1/kecamatan/search", "/v1/kelurahan/search")
                .permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }

}
