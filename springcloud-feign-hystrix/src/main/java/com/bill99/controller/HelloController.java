package com.bill99.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill99.service.HelloFeignClient;

@RestController
public class HelloController {
	
	@Autowired
	private HelloFeignClient helloFeignClient;
	
	@RequestMapping(value = "/hello")
	public String feignHello(){
		return helloFeignClient.hello();
	}

}
