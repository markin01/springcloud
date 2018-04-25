package com.bill99.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RibbonClient {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String hello(){
		return restTemplate.getForObject("http://springcloud-provider-service/hello", String.class);
	}

}
