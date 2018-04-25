package com.bill99;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class SpringCloudOauth2App {
	
	public static void main(String[] args) {
        SpringApplication.run(SpringCloudOauth2App.class, args);
    }

}
