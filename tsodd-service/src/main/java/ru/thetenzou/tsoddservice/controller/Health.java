package ru.thetenzou.tsoddservice.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tsodd")
public class Health {

    @GetMapping("/health")
    public Map getHealth() {
        return Collections.singletonMap("status", "live");
    }
    
}
