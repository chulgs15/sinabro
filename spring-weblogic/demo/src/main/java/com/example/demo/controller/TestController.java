package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/ping")
    public String test() {
        return "hello world";
    }

    // 그냥 테스트임

}
