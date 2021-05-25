package ru.thetenzou.tsoddservice.schedule.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.thetenzou.tsoddservice.schedule.dto.request.ScheduledTaskRequestDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduledTaskDetailDto
import ru.thetenzou.tsoddservice.schedule.service.ScheduledTaskService

/**
 * A ScheduleController is endpoint for scheduled tasks
 */
@RestController
@RequestMapping("/api/tsodd/v1/scheduledTask")
class ScheduledTaskController(
    private val scheduledTaskService: ScheduledTaskService,
) {

    /**
     * createScheduledTask create new scheduled task
     *
     * @param request holds incoming request body
     *
     * @return saved scheduled task
     */
    @PutMapping
    fun createScheduledTask(@RequestBody request: ScheduledTaskRequestDto): ResponseEntity<ScheduledTaskDetailDto> {

        val scheduledTask = scheduledTaskService.createScheduledTask(request)

        return ResponseEntity(scheduledTask, HttpStatus.CREATED)

    }
}