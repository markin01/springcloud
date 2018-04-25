package com.bill99.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill99.service.FeignClients;

@RestController
public class FeignController {
	
	@Autowired
	private FeignClients feignClients;
	
	@RequestMapping(value = "/hello")
	public String feignHello(){
		return feignClients.hello();
	}

}
