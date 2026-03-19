package com.manuel.anchorlife.anchorlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class AnchorlifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnchorlifeApplication.class, args);
    }
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
