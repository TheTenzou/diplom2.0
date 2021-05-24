package ru.thetenzou.tsoddservice.common.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/tsodd/v1")
class Hello {
    @GetMapping("/helloWorld")
    fun hello(): String {
        return "hello world"
    }

    @GetMapping("/helloUser")
    fun helloUser(): String {
        return "hello user"
    }

    @GetMapping("/helloAdmin")
    fun helloAdmin(): String {
        return "hello admin"
    }
}
