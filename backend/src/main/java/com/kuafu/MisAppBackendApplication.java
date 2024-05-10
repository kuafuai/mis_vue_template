package com.kuafu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MisAppBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MisAppBackendApplication.class, args);
    }
}
