package com.example.furniture_factory.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config{
    @Bean
    public Map<String,String> session(){
        return new HashMap<>();
    }
}
