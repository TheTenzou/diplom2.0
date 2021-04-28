package ru.thetenzou.tsoddservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/tsodd/v1")
public class Hello {
    
    @GetMapping(value="/hello")
    public String helloString() {
        return new String("hello world");
    }
    
}
