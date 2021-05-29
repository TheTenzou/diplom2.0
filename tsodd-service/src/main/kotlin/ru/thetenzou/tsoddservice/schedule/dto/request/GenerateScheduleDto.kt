package ru.thetenzou.tsoddservice.schedule.dto.request

import java.time.LocalDate

data class GenerateScheduleDto(
    val name: String,
    val resourcesLimit:Double,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
