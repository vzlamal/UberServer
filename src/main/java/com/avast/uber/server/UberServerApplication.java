package com.avast.uber.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UberServerApplication {

    public static final String CONTEXT_PATH = "/v1";
    public static final String VIP_API_BASE_URI = "http://localhost:8088/v1/coords/";

    public static void main(String[] args) {
        SpringApplication.run(UberServerApplication.class, args);
    }

}
