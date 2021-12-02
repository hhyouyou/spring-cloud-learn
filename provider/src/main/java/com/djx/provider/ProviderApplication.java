package com.djx.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@EnableConfigurationProperties(AutoServiceRegistrationProperties.class)
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }


    @RestController
    static class EchoController {

        @GetMapping("/echo")
        public String echo(HttpServletRequest request) {

            String value = request.getParameter("name");
            System.out.println(value);
            return "echo:" + value;
        }
    }

}
