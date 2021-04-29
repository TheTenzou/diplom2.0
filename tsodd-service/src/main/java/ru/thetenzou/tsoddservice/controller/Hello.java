package ru.thetenzou.tsoddservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/tsodd/v1")
public class Hello {
    
    @GetMapping(value="/helloWorld")
    public String hello() {
        return new String("hello world");
    }

    @GetMapping(value="/helloUser")
    public String helloUser() {
        return new String("hello user");
    }

    @GetMapping(value="/helloAdmin")
    public String helloAdmin() {
        return new String("hello admin");
    }
    
}
