package com.example.UserManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.security.SecureRandom;
import java.util.Base64;


    @EnableJpaAuditing
    @SpringBootApplication
public class FullStackApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullStackApplication.class, args);

    }

}
