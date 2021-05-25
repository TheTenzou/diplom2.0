package ru.thetenzou.tsoddservice.schedule.dto.request

import java.time.LocalDate

/**
 * Request dto
 * required for parsing incoming request for ScheduledTaskController
 */
data class ScheduledTaskRequestDto(
    val id: Long,
    val schedule: Long,
    val date: LocalDate,
    val tsodd: Long,
    val taskType: Long,
    val crew: Long,
)
