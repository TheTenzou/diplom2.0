package ru.thetenzou.tsoddservice.schedule.dto

import ru.thetenzou.tsoddservice.schedule.model.Schedule
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Schedule response data transfer object
 */
data class ScheduleDto(
    var id: Long,
    var name: String,
    var createdDate: LocalDateTime,
    var startDate: LocalDate,
    var endDate: LocalDate,
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
