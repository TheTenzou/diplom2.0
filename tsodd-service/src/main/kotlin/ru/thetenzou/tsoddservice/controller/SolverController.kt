package ru.thetenzou.tsoddservice.controller

import org.springframework.web.bind.annotation.*
import ru.thetenzou.tsoddservice.request.SolveRequestDto
import ru.thetenzou.tsoddservice.service.ScheduleGenerator


@RestController
@RequestMapping("/api/tsodd/v1")
class SolverController(
    private val schedulePlanningService: ScheduleGenerator
) {

    @PostMapping("/solve")
    fun solve(@RequestBody request: SolveRequestDto): String {

        schedulePlanningService.generateSchedule(request.name, request.startDate, request.endDate)

        return "look at console"
    }

}