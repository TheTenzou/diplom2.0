package ru.thetenzou.tsoddservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TsoddServiceApplication

fun main(args: Array<String>) {
	runApplication<TsoddServiceApplication>(*args)
}
