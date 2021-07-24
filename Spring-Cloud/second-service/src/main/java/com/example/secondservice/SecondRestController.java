package com.example.secondservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/second-services")
@Slf4j
public class SecondRestController {

    @GetMapping(value = {"/welcome"})
    public String welcome() {
        return "Welcome to the Second service.";
    }

    @GetMapping(value = {"/message"})
    public String message(@RequestHeader("second-request") String header) {
        log.info(header);
        return "Hello World in Second Service";
    }
}
