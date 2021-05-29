package ru.thetenzou.tsoddservice.schedule.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDetailDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDto
import ru.thetenzou.tsoddservice.schedule.service.ScheduleService
import ru.thetenzou.tsoddservice.common.dto.PagedResponse
import ru.thetenzou.tsoddservice.schedule.dto.request.ScheduleRequestDto
import ru.thetenzou.tsoddservice.schedule.dto.request.GenerateScheduleDto
import ru.thetenzou.tsoddservice.schedule.service.solver.ScheduleGenerator

/**
 * A ScheduleController is endpoint for schedule
 */
@RestController
@RequestMapping("/api/tsodd/v1/schedule")
class ScheduleController(
    private val scheduleService: ScheduleService,
    private val schedulePlanningService: ScheduleGenerator,
) {

    /**
     * generateSchedule starts process of generating schedule
     *
     * @param request incoming request
     *
     * @return generating schedule
     */
    @PostMapping("/generate")
    fun generateNewSchedule(@RequestBody request: GenerateScheduleDto): ResponseEntity<ScheduleDto> {

        val schedule = schedulePlanningService.generateSchedule(request.name, request.resourcesLimit, request.startDate, request.endDate)

        return ResponseEntity(schedule, HttpStatus.ACCEPTED)
    }

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

        val response = scheduleService.getAllSchedules(page - 1, size)

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

    /**
     * createSchedule create new empty schedule
     *
     * @param scheduleRequest holds incoming request body
     *
     * @return saved schedule
     */
    @PutMapping
    fun createSchedule(@RequestBody scheduleRequest: ScheduleRequestDto): ResponseEntity<ScheduleDto> {

        val schedule = scheduleService.createSchedule(scheduleRequest)

        return ResponseEntity(schedule, HttpStatus.CREATED)
    }

    /**
     * updateSchedule update existing schedule
     *
     * @param scheduleRequest holds incoming request body
     *
     * @return updated schedule
     */
    @PatchMapping
    fun updateSchedule(@RequestBody scheduleRequest: ScheduleRequestDto): ResponseEntity<ScheduleDto> {

        val schedule = scheduleService.updateSchedule(scheduleRequest)

        return ResponseEntity(schedule, HttpStatus.OK)
    }

    /**
     * deleteSchedule delete existing schedule
     *
     * @param id id of the schedule
     *
     * @return deleted schedule
     */
    @DeleteMapping("/{id}")
    fun deleteSchedule(@PathVariable id: Long): ResponseEntity<ScheduleDto> {

        val schedule = scheduleService.deleteSchedule(id)

        return ResponseEntity(schedule, HttpStatus.OK)
    }

}