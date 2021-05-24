package ru.thetenzou.tsoddservice.schedule.service

import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDetailDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDto
import ru.thetenzou.tsoddservice.common.dto.PagedResponse

/**
 * A ScheduleService
 */
interface ScheduleService {

    /**
     * getAllSchedules returns paged schedule data according to pageable parameters
     *
     * @param page number of the page
     * @param size size of the page
     *
     * @return paged response
     */
    fun getAllSchedules(page: Int, size: Int): PagedResponse<ScheduleDto>

    /**
     * getScheduleById finds required schedule
     *
     * @param id id of the schedule
     * @param page number of the page
     * @param size size of the page
     *
     * @return schedule dto
     */
    fun getScheduleById(id: Long, page: Int, size: Int): ScheduleDetailDto
}