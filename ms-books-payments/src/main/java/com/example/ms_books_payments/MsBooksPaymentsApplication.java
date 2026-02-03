package com.example.ms_books_payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MsBooksPaymentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsBooksPaymentsApplication.class, args);
    }

    @Bean
    // @LoadBalanced <-- Eureka
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
