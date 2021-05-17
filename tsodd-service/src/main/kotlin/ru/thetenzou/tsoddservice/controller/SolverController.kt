package ru.thetenzou.tsoddservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.thetenzou.tsoddservice.service.SchedulePlanningService


@RestController
@RequestMapping("/api/tsodd/v1")
class SolverController(
    private val schedulePlanningService: SchedulePlanningService
) {

    @GetMapping("/solve")
    fun solve(): String {

        schedulePlanningService.planSchedule()

        return "look at console"
    }

}