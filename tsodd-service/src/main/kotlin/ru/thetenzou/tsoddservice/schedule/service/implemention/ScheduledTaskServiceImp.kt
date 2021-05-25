package ru.thetenzou.tsoddservice.schedule.service.implemention

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.thetenzou.tsoddservice.crew.repository.CrewRepository
import ru.thetenzou.tsoddservice.schedule.dto.request.ScheduledTaskRequestDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduledTaskDetailDto
import ru.thetenzou.tsoddservice.schedule.model.ScheduledTask
import ru.thetenzou.tsoddservice.schedule.repository.ScheduleRepository
import ru.thetenzou.tsoddservice.schedule.repository.ScheduledTaskRepository
import ru.thetenzou.tsoddservice.schedule.service.ScheduledTaskService
import ru.thetenzou.tsoddservice.task.repository.TaskTypeRepository
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddRepository
import javax.persistence.EntityNotFoundException

@Service
class ScheduledTaskServiceImp(
    private val scheduleRepository: ScheduleRepository,
    private val scheduledTaskRepository: ScheduledTaskRepository,

    private val tsoddRepository: TsoddRepository,
    private val taskTypeRepository: TaskTypeRepository,
    private val crewRepository: CrewRepository,
) : ScheduledTaskService {

    override fun createScheduledTask(scheduledTaskDto: ScheduledTaskRequestDto): ScheduledTaskDetailDto {

        val scheduledTask = convertScheduledTaskRequestToScheduledTask(scheduledTaskDto)

        val savedScheduledTask = scheduledTaskRepository.save(scheduledTask)

        logger.info("Scheduled task with id: ${savedScheduledTask.id} has benn created")

        return ScheduledTaskDetailDto(savedScheduledTask)
    }

    override fun updateScheduledTask(scheduledTaskDto: ScheduledTaskRequestDto): ScheduledTaskDetailDto {

        val scheduledTask = convertScheduledTaskRequestToScheduledTask(scheduledTaskDto)

        if (!scheduledTaskRepository.existsById(scheduledTaskDto.id)) {
            throw EntityNotFoundException("Scheduled task with id: ${scheduledTaskDto.id} doesn't exist")
        }
        scheduledTask.id = scheduledTaskDto.id

        val savedScheduledTask = scheduledTaskRepository.save(scheduledTask)

        logger.info("Scheduled task with id: ${savedScheduledTask.id} has benn updated")

        return ScheduledTaskDetailDto(savedScheduledTask)
    }

    private fun convertScheduledTaskRequestToScheduledTask(scheduledTaskRequestDto: ScheduledTaskRequestDto): ScheduledTask {

        val scheduleOptional = scheduleRepository.findById(scheduledTaskRequestDto.schedule)
        if (scheduleOptional.isEmpty) {
            throw EntityNotFoundException("Schedule with id: ${scheduledTaskRequestDto.schedule} doesn't exist")
        }
        val schedule = scheduleOptional.get()

        val tsoddOptional = tsoddRepository.findById(scheduledTaskRequestDto.tsodd)
        if (tsoddOptional.isEmpty) {
            throw EntityNotFoundException("Tsodd with id: ${scheduledTaskRequestDto.tsodd} doesn't exist")
        }
        val tsodd = tsoddOptional.get()

        val taskTypeOptional = taskTypeRepository.findById(scheduledTaskRequestDto.taskType)
        if (taskTypeOptional.isEmpty) {
            throw EntityNotFoundException("Task type with id: ${scheduledTaskRequestDto.taskType} doesn't exist")
        }
        val taskType = taskTypeOptional.get()

        val crewOptional = crewRepository.findById(scheduledTaskRequestDto.crew)
        if (crewOptional.isEmpty) {
            throw EntityNotFoundException("Crew with id: ${scheduledTaskRequestDto.crew} doesn't exist")
        }
        val crew = crewOptional.get()

        return ScheduledTask(
            id = null,
            schedule = schedule,
            date = scheduledTaskRequestDto.date,
            tsodd = tsodd,
            taskType = taskType,
            crew = crew,
        )
    }

    companion object{
        val logger = LoggerFactory.getLogger(ScheduledTaskService::class.java)
    }
}