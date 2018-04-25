package com.bill99.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("springcloud-provider-service")
public interface FeignClients {
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello();

}
