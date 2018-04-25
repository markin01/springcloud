package com.bill99.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping(value = "show", method = RequestMethod.GET)
    public String home() {
        return "Hello World!";
    }

}
