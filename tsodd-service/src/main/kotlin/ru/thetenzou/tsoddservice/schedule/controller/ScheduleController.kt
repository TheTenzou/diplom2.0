package ru.thetenzou.tsoddservice.schedule.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDetailDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDto
import ru.thetenzou.tsoddservice.schedule.service.ScheduleService
import ru.thetenzou.tsoddservice.util.PagedResponse

/**
 * A ScheduleController is endpoint for schedule
 */
@RestController
@RequestMapping("/api/tsodd/v1/schedule")
class ScheduleController(
    private val scheduleService: ScheduleService,
) {

    /**
     * getAllSchedules return paged response
     *
     * @param page page number
     * @param size page size
     *
     * @return paged response
     */
    @GetMapping()
    fun getAllSchedules(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<PagedResponse<ScheduleDto>> {

        val response = scheduleService.getAllSchedules(page, size)

        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getScheduleById(
        @PathVariable id: Long,
    ): ResponseEntity<ScheduleDetailDto> {

        val schedule = scheduleService.getScheduleById(id)

        return ResponseEntity(schedule, HttpStatus.OK)
    }
}