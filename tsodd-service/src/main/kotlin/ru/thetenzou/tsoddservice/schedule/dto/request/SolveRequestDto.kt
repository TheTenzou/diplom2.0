package ru.thetenzou.tsoddservice.schedule.dto.request

import java.time.LocalDate

data class SolveRequestDto(
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
