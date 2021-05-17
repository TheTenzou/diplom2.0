package ru.thetenzou.tsoddservice.controller

import org.springframework.web.bind.annotation.*
import ru.thetenzou.tsoddservice.request.SolveRequestDto
import ru.thetenzou.tsoddservice.service.SchedulePlanningService


@RestController
@RequestMapping("/api/tsodd/v1")
class SolverController(
    private val schedulePlanningService: SchedulePlanningService
) {

    @PostMapping("/solve")
    fun solve(@RequestBody request: SolveRequestDto): String {

        schedulePlanningService.planSchedule(request.name, request.startDate, request.endDate)

        return "look at console"
    }

}