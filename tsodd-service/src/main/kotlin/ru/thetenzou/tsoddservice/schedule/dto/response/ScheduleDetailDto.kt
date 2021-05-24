package ru.thetenzou.tsoddservice.schedule.dto.response

import org.springframework.data.domain.Page
import ru.thetenzou.tsoddservice.schedule.model.Schedule
import ru.thetenzou.tsoddservice.schedule.model.ScheduledTask
import ru.thetenzou.tsoddservice.util.PagedResponse
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Schedule response data transfer object
 */
data class ScheduleDetailDto (
    val id: Long,
    val name: String,
    val createdDate: LocalDateTime,
    val startDate: LocalDate,
    val endDate: LocalDate,
//    val taskList: List<ScheduledTaskDto>,
    val tasks: PagedResponse<ScheduledTaskDto>
) {

    /**
     * convert schedule model to dto
     */
    constructor(schedule: Schedule, tasks: Page<ScheduledTaskDto>) : this(
        id = schedule.id,
        name = schedule.name,
        createdDate = schedule.createdDate,
        startDate = schedule.startDate,
        endDate = schedule.endDate,
        tasks = PagedResponse(tasks),
    )
}
