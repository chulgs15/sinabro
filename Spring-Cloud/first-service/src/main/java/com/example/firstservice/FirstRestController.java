package com.example.firstservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-services")
public class FirstRestController {

    @GetMapping(value = {"/welcome"})
    public String welcome() {
        return "Welcome to the first service.";
    }
}
