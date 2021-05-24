package ru.thetenzou.tsoddservice.schedule.dto

import ru.thetenzou.tsoddservice.schedule.model.Schedule
import ru.thetenzou.tsoddservice.schedule.model.ScheduledTask
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Schedule response data transfer object
 */
data class ScheduleDetailDto(
    val id: Long,
    val name: String,
    val createdDate: LocalDateTime,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val taskList: List<ScheduledTaskDto>,
) {

    /**
     * convert schedule model to dto
     */
    constructor(schedule: Schedule, tasks: List<ScheduledTask>) : this(
        id = schedule.id,
        name = schedule.name,
        createdDate = schedule.createdDate,
        startDate = schedule.startDate,
        endDate = schedule.endDate,
        taskList = tasks.map { ScheduledTaskDto(it) }
    )
}
