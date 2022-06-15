package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.api.repository"})
@EntityScan(basePackages = {"com.api.model"})
//@EnableScheduling
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}