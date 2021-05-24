package ru.thetenzou.tsoddservice.schedule.dto.request

import java.time.LocalDate

/**
 * Request dto
 * required for parsing incoming request for SchedulConntroller
 */
data class ScheduleRequestDto(
    val id: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
