package com.example.searchena;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@EnableConfigurationProperties
@SpringBootApplication
public class SearchenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchenaApplication.class, args);
    }

}
