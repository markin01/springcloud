package com.bill99.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.bill99.springboot.mapper")
public class SpringbootApp{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApp.class, args);
	}

}
