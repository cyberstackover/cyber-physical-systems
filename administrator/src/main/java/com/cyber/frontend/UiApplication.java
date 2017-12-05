package com.cyber.frontend;

import com.cyber.Matagaruda;
import com.cyber.authentication.GrantType;
import com.cyber.exceptions.MatagarudaException;
import com.cyber.exceptions.MatagarudaOAuthException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@Controller
public class UiApplication { //extends SpringBootServletInitializer {

//    @Override
 //   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
  //      return application.sources(UiApplication.class);
 //   }

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {

                ErrorPage error500Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
                ErrorPage error404Page = new ErrorPage(HttpStatus.OK, "/index.html");
                ErrorPage error401Page = new ErrorPage(HttpStatus.FOUND, "/index.html");

                container.addErrorPages(error500Page, error404Page, error401Page);
            }
        };
    }

    @Bean
    public Matagaruda sakaNusantara() throws MatagarudaException, MatagarudaOAuthException {
        String[] scopes = {"insert","select","update","delete"};
        Matagaruda client = new Matagaruda().
                setClientId("administrator").
                setClientSecret("administrator-secret").
                setRedirectUrl("http://iout-er2c.com:8000/administrator/redirect").
                setScopes(scopes).
                setGrantType(GrantType.AUTHORIZATION_CODE).
                build();
        return client;
    }
}
