package ru.thetenzou.tsoddservice.schedule.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.thetenzou.tsoddservice.schedule.dto.request.ScheduledTaskRequestDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDto
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

    /**
     * updateScheduledTask update given schedule
     *
     * @param request holds incoming request body
     *
     * @return update scheduled task
     */
    @PatchMapping
    fun updateScheduledTask(@RequestBody request: ScheduledTaskRequestDto): ResponseEntity<ScheduledTaskDetailDto> {
        if (request.id == 0L) {
            throw IllegalArgumentException("")
        }
        val scheduledTask = scheduledTaskService.updateScheduledTask(request)

        return ResponseEntity(scheduledTask, HttpStatus.OK)
    }

    /**
     * deleteScheduledTask delete existing schedule
     *
     * @param id id of the scheduled task
     *
     * @return deleted scheduled task
     */
    @DeleteMapping("/{id}")
    fun deleteScheduledTask(@PathVariable id: Long): ResponseEntity<ScheduledTaskDetailDto> {

        val schedule = scheduledTaskService.deleteScheduledTask(id)

        return ResponseEntity(schedule, HttpStatus.OK)
    }
}