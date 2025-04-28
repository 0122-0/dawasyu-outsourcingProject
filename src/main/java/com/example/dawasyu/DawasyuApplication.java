package com.example.dawasyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DawasyuApplication {

    public static void main(String[] args) {
        SpringApplication.run(DawasyuApplication.class, args);
    }
}
