package ru.thetenzou.tsoddservice.schedule.controller

import org.springframework.web.bind.annotation.*
import ru.thetenzou.tsoddservice.schedule.request.SolveRequestDto
import ru.thetenzou.tsoddservice.schedule.service.ScheduleGenerator


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