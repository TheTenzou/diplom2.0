package ru.thetenzou.tsoddservice.schedule.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDetailDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDto
import ru.thetenzou.tsoddservice.schedule.service.ScheduleService
import ru.thetenzou.tsoddservice.common.dto.PagedResponse

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

    /**
     * getScheduleById returns schedule details
     * list of scheduled tasks paginated
     *
     * @param id id of the schedule
     * @param page number of page
     * @param size size of the page
     *
     * @return paged response
     */
    @GetMapping("/{id}")
    fun getScheduleById(
        @PathVariable id: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "100") size: Int,
    ): ResponseEntity<ScheduleDetailDto> {

        val schedule = scheduleService.getScheduleById(id, page, size)

        return ResponseEntity(schedule, HttpStatus.OK)
    }
}