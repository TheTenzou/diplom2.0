package ru.thetenzou.tsoddservice.schedule.service

import org.springframework.data.domain.Pageable
import ru.thetenzou.tsoddservice.schedule.dto.response.SchedulePageResponse

/**
 * A ScheduleService
 */
interface ScheduleService {

    /**
     * getAllSchedules returns paged schedule data according to pageable parameters
     *
     * @param pageable define pagination
     *
     * @return paged response
     */
    fun getAllSchedules(pageable: Pageable): SchedulePageResponse
}