package ru.thetenzou.tsoddservice.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import ru.thetenzou.tsoddservice.crew.dto.response.CrewDto
import ru.thetenzou.tsoddservice.schedule.model.ScheduledTask
import ru.thetenzou.tsoddservice.task.dto.response.TaskTypeMinimalDto
import ru.thetenzou.tsoddservice.tsodd.dto.response.TsoddMinimalDto
import java.time.LocalDate

/**
 * A ScheduledTaskDetailDto is data transfer object for scheduled task
 */
data class ScheduledTaskDetailDto(
    val id: Long?,
    val schedule: ScheduleDto,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val date: LocalDate?,
    val tsodd: TsoddMinimalDto,
    val taskType: TaskTypeMinimalDto,
    val crew: CrewDto,
) {

    /**
     * Init ScheduledTaskDetailDto base on given ScheduledTask
     */
    constructor(scheduledTask: ScheduledTask) : this(
        id = scheduledTask.id,
        schedule = ScheduleDto(scheduledTask.schedule!!),
        date = scheduledTask.date,
        tsodd = TsoddMinimalDto(scheduledTask.tsodd),
        taskType = TaskTypeMinimalDto(scheduledTask.taskType),
        crew = CrewDto(scheduledTask.crew),
    )
}
