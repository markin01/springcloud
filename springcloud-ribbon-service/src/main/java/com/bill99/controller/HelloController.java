package com.bill99.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill99.service.RibbonClient;

@RestController
public class HelloController {
	
	@Autowired
	private RibbonClient ribbonClient;
	
	@RequestMapping(value = "/hello")
	public String hello(){
		return ribbonClient.hello();
	}
	


}
