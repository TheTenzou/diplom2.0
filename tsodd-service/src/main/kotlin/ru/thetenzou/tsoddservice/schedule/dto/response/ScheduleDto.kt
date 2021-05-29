package ru.thetenzou.tsoddservice.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import ru.thetenzou.tsoddservice.schedule.model.Schedule
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Schedule response data transfer object
 */
data class ScheduleDto(
    val id: Long,
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    val createdDate: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val startDate: LocalDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val endDate: LocalDate,
    val status: String,
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
        status = schedule.status.name
    )
}
