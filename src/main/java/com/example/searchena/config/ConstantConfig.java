package com.example.searchena.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@PropertySource({"classpath:ena.properties"})
@ConfigurationProperties(prefix = "ena")
public class ConstantConfig {

    private String url;

}
