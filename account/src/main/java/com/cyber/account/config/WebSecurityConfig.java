package com.cyber.account.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login?error=true")
                .and()
                // TODO: put CSRF protection back into this endpoint
                //          .csrf()
                //        .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                //        .disable()
                .formLogin()
                .failureUrl("/login?error=true")
                .loginPage("/login")
                .and()
                .logout()
                .logoutUrl("/login?logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/")
                .and()
                .rememberMe();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
//                    .withUser("admin").password(passwordEncoder.encode("admin")).roles("SUPER_ADMIN", "DEVELOPER", "USER").accountLocked(false)
//                .and()
//                    .withUser("developer").password(passwordEncoder.encode("admin")).roles("DEVELOPER", "USER").accountLocked(false)
//                .and()
//                    .withUser("user").password(passwordEncoder.encode("admin")).roles("USER").accountLocked(false);
    }
}
