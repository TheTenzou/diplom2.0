package ru.thetenzou.tsoddservice.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import ru.thetenzou.tsoddservice.crew.dto.response.CrewDto
import ru.thetenzou.tsoddservice.schedule.model.ScheduledTask
import ru.thetenzou.tsoddservice.task.dto.response.TaskTypeMinimalDto
import ru.thetenzou.tsoddservice.tsodd.dto.response.TsoddMinimalDto
import java.time.LocalDate

/**
 * A ScheduledTaskDto is data transfer object for scheduled task
 */
data class ScheduledTaskDto(
    val id: Long?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val date: LocalDate?,
    val tsodd: TsoddMinimalDto,
    val taskType: TaskTypeMinimalDto,
    val crew: CrewDto,
) {

    /**
     * Init ScheduledTaskDto base on given ScheduledTask
     */
    constructor(scheduledTask: ScheduledTask) : this(
        id = scheduledTask.id,
        date = scheduledTask.date,
        tsodd = TsoddMinimalDto(scheduledTask.tsodd),
        taskType = TaskTypeMinimalDto(scheduledTask.taskType),
        crew = CrewDto(scheduledTask.crew),
    )
}
