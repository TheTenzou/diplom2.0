package ru.thetenzou.tsoddservice.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.domain.Page
import ru.thetenzou.tsoddservice.schedule.model.Schedule
import ru.thetenzou.tsoddservice.common.dto.PagedResponse
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Schedule response data transfer object
 */
data class ScheduleDetailDto (
    val id: Long,
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    val createdDate: LocalDateTime,
    val resourceLimit: Double,
    val totalResources: Double,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val startDate: LocalDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val endDate: LocalDate,
    val status: String,
    val tasks: PagedResponse<ScheduledTaskDto>
) {

    /**
     * convert schedule model to dto
     */
    constructor(schedule: Schedule, tasks: Page<ScheduledTaskDto>) : this(
        id = schedule.id,
        name = schedule.name,
        createdDate = schedule.createdDate,
        resourceLimit = schedule.resourceLimit,
        totalResources = schedule.totalResources,
        startDate = schedule.startDate,
        endDate = schedule.endDate,
        status = schedule.status.name,
        tasks = PagedResponse(tasks),
    )
}
