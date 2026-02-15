package com.sala.userauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.sala.userauth.repository")
@EntityScan(basePackages = "com.sala.userauth.model") 
public class UserauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserauthApplication.class, args);
    }
}