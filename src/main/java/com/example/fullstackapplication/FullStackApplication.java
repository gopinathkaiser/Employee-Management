package com.example.fullstackapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class FullStackApplication {

    public static void main(String[] args) {

        SpringApplication.run(FullStackApplication.class, args);
    }

}
