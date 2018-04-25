package com.bill99.springboot.configuration;

import java.util.Map;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bill99.springboot.endpoint.SystemEndPoint;

@Configuration
public class EndPointAutoConfig {
	
    @Bean
    public Endpoint<Map<String, Object>> systemEndPoint() {
        return new SystemEndPoint();
    }
    
}