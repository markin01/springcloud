package com.bill99.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bill99.fallback.HelloFeignClientFallback;

@FeignClient(name = "springcloud-provider-service", fallback = HelloFeignClientFallback.class)
public interface HelloFeignClient {
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello();

}
