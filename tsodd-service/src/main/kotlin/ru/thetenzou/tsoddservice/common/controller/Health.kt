package ru.thetenzou.tsoddservice.common.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/api/tsodd")
class Health {

    @GetMapping("/health")
    fun health() = Collections.singletonMap("status", "live")

}
