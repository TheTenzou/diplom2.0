package ru.thetenzou.tsoddservice.schedule.dto.response

import ru.thetenzou.tsoddservice.crew.model.Crew
import ru.thetenzou.tsoddservice.schedule.model.ScheduledTask
import ru.thetenzou.tsoddservice.task.model.TaskType
import ru.thetenzou.tsoddservice.tsodd.model.Tsodd
import java.time.LocalDate

data class ScheduledTaskDto(
    val id: Long?,
    val date: LocalDate?,
    val tsodd: Tsodd,
    val taskType: TaskType,
    val crew: Crew,
) {
    constructor(scheduledTask: ScheduledTask) : this(
        id = scheduledTask.id,
        date = scheduledTask.date,
        tsodd = scheduledTask.tsodd,
        taskType = scheduledTask.taskType,
        crew = scheduledTask.crew,
    )
}
