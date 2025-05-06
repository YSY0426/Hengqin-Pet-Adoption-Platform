package com.gk.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")  // 访问 http://localhost:9100/api/test
    public String testApi() {
        System.out.println(">>> TestController OK！");
        return "Hello, API! ";
    }

}