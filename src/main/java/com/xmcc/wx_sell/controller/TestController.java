package com.xmcc.wx_sell.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "hello spring-boot 改动代码git测试aa";
    }
}
