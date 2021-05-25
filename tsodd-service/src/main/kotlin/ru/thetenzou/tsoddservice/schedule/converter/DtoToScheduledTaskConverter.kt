package ru.thetenzou.tsoddservice.schedule.converter

import org.springframework.stereotype.Component
import ru.thetenzou.tsoddservice.crew.model.Crew
import ru.thetenzou.tsoddservice.crew.repository.CrewRepository
import ru.thetenzou.tsoddservice.schedule.dto.request.ScheduledTaskRequestDto
import ru.thetenzou.tsoddservice.schedule.model.Schedule
import ru.thetenzou.tsoddservice.schedule.model.ScheduledTask
import ru.thetenzou.tsoddservice.schedule.repository.ScheduleRepository
import ru.thetenzou.tsoddservice.task.model.TaskType
import ru.thetenzou.tsoddservice.task.repository.TaskTypeRepository
import ru.thetenzou.tsoddservice.tsodd.model.Tsodd
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddRepository
import javax.persistence.EntityNotFoundException

/**
 * provide functionality to convert scheduled task from dto to model
 */
@Component
class DtoToScheduledTaskConverter(
    private val scheduleRepository: ScheduleRepository,
    private val tsoddRepository: TsoddRepository,
    private val taskTypeRepository: TaskTypeRepository,
    private val crewRepository: CrewRepository,
) {

    /**
     * convert scheduled task dto to model
     */
    fun convert(scheduledTaskRequestDto: ScheduledTaskRequestDto): ScheduledTask {

        val schedule = getSchedule(scheduledTaskRequestDto.schedule)

        val tsodd = getTsodd(scheduledTaskRequestDto.tsodd)

        val taskType = getTaskType(scheduledTaskRequestDto.taskType)

        val crew = getCrew(scheduledTaskRequestDto.crew)

        return ScheduledTask(
            id = null,
            schedule = schedule,
            date = scheduledTaskRequestDto.date,
            tsodd = tsodd,
            taskType = taskType,
            crew = crew,
        )
    }

    private fun getSchedule(scheduleId: Long): Schedule {
        val schedule = scheduleRepository.findById(scheduleId)
        if (schedule.isEmpty) {
            throw EntityNotFoundException("Schedule with id: $scheduleId doesn't exist")
        }
        return schedule.get()
    }

    private fun getTsodd(tsoddId: Long): Tsodd {
        val tsodd = tsoddRepository.findById(tsoddId)
        if (tsodd.isEmpty) {
            throw EntityNotFoundException("Tsodd with id: $tsoddId doesn't exist")
        }
        return tsodd.get()
    }

    private fun getTaskType(taskTypeId: Long): TaskType {
        val taskType = taskTypeRepository.findById(taskTypeId)
        if (taskType.isEmpty) {
            throw EntityNotFoundException("Task type with id: $taskTypeId doesn't exist")
        }
        return taskType.get()
    }

    private fun getCrew(crewId: Long): Crew {
        val crew = crewRepository.findById(crewId)
        if (crew.isEmpty) {
            throw EntityNotFoundException("Crew with id: $crewId doesn't exist")
        }
        return crew.get()
    }
}