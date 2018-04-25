package com.bill99;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient //激活eureka中的DiscoveryClient实现
@SpringBootApplication
public class ProviderApp {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ProviderApp.class).web(true).run(args);
	}

}
