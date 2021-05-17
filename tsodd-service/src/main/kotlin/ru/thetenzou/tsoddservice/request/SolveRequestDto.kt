package ru.thetenzou.tsoddservice.request

import java.time.LocalDate

data class SolveRequestDto(
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
