package com.example.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/first-services")
@Slf4j
public class FirstRestController {

    private final Environment environment;

    public FirstRestController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping(value = {"/welcome"})
    public String welcome() {
        return "Welcome to the first service.";
    }

    @GetMapping(value = {"/message"})
    public String message(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Hello World in First Service";
    }

    @GetMapping(value = {"/check"})
    public String check(HttpServletRequest request) {
        log.info("HttpServletRequest server port is " + request.getServerPort());
        String message = "Environment server port is " + environment.getProperty("local.server.port");
        log.info(message);
        return message;
    }
}
