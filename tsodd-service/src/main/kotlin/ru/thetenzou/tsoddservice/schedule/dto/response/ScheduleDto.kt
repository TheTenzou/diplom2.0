package ru.thetenzou.tsoddservice.schedule.dto.response

import ru.thetenzou.tsoddservice.schedule.model.Schedule
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Schedule response data transfer object
 */
data class ScheduleDto(
    val id: Long,
    val name: String,
    val createdDate: LocalDateTime,
    val startDate: LocalDate,
    val endDate: LocalDate,
) {

    /**
     * convert schedule model to dto
     */
    constructor(schedule: Schedule) : this(
        id = schedule.id,
        name = schedule.name,
        createdDate = schedule.createdDate,
        startDate = schedule.startDate,
        endDate = schedule.endDate,
    )
}
