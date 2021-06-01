package ru.uds.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/uds/v1")
public class TestController {

    @GetMapping
    public String hello() {
        return "hello world";
    }
}
