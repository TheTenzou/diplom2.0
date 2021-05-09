package ru.thetenzou.tsoddservice

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import ru.thetenzou.tsoddservice.model.Test
import ru.thetenzou.tsoddservice.repository.TestRepository

@SpringBootApplication
class TsoddServiceApplication {

	private val log = LoggerFactory.getLogger(TsoddServiceApplication::class.java)

	@Bean
	fun init(testRepository: TestRepository) = CommandLineRunner {
		val test = Test(
			id = 0L,
			point = GeometryFactory().createPoint(Coordinate(1.0, 1.0))
		)

		testRepository.save(test)

		log.info("New record $test")
	}
}

fun main(args: Array<String>) {
	runApplication<TsoddServiceApplication>(*args)
}
