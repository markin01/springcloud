package com.bill99.fallback;

import org.springframework.stereotype.Component;

import com.bill99.service.HelloFeignClient;

@Component
public class HelloFeignClientFallback implements HelloFeignClient{

	public String hello() {
		return "HelloFeignClient fallback";
	}

}
