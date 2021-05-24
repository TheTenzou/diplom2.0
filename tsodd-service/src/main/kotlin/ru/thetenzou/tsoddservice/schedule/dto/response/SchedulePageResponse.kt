package ru.thetenzou.tsoddservice.schedule.dto.response

import ru.thetenzou.tsoddservice.schedule.dto.ScheduleDto

/**
 * Schedule paged response structure
 */
data class SchedulePageResponse(
    val total: Long,
    val page: Int,
    val pageSize: Int,
    val previousPage: Int,
    val nextPage: Int,
    val totalPages: Int,
    val data: List<ScheduleDto>,
)
