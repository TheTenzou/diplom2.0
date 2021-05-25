package ru.thetenzou.tsoddservice.schedule.service.implemention

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.thetenzou.tsoddservice.schedule.service.converter.DtoToScheduledTaskConverter
import ru.thetenzou.tsoddservice.schedule.dto.request.ScheduledTaskRequestDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduledTaskDetailDto
import ru.thetenzou.tsoddservice.schedule.repository.ScheduledTaskRepository
import ru.thetenzou.tsoddservice.schedule.service.ScheduledTaskService
import javax.persistence.EntityNotFoundException

@Service
class ScheduledTaskServiceImp(
    private val scheduledTaskRepository: ScheduledTaskRepository,

    private val dtoToScheduledTaskConverter: DtoToScheduledTaskConverter,
) : ScheduledTaskService {

    override fun createScheduledTask(scheduledTaskDto: ScheduledTaskRequestDto): ScheduledTaskDetailDto {

        val scheduledTask = dtoToScheduledTaskConverter.convert(scheduledTaskDto)

        val savedScheduledTask = scheduledTaskRepository.save(scheduledTask)

        logger.info("Scheduled task with id: ${savedScheduledTask.id} has benn created")

        return ScheduledTaskDetailDto(savedScheduledTask)
    }

    override fun updateScheduledTask(scheduledTaskDto: ScheduledTaskRequestDto): ScheduledTaskDetailDto {

        val scheduledTask = dtoToScheduledTaskConverter.convert(scheduledTaskDto)

        if (!scheduledTaskRepository.existsById(scheduledTaskDto.id)) {
            throw EntityNotFoundException("Scheduled task with id: ${scheduledTaskDto.id} doesn't exist")
        }
        scheduledTask.id = scheduledTaskDto.id

        val savedScheduledTask = scheduledTaskRepository.save(scheduledTask)

        logger.info("Scheduled task with id: ${savedScheduledTask.id} has benn updated")

        return ScheduledTaskDetailDto(savedScheduledTask)
    }

    override fun deleteScheduledTask(scheduledTaskId: Long): ScheduledTaskDetailDto {

        val results = scheduledTaskRepository.findById(scheduledTaskId)

        if (results.isEmpty) {
            throw EntityNotFoundException("schedule with id: $scheduledTaskId doesn't exist")
        }
        val schedule = results.get()

        scheduledTaskRepository.delete(schedule)

        ScheduleServiceImpl.logger.info("Schedule with id: $scheduledTaskId has been deleted")

        return ScheduledTaskDetailDto(schedule)
    }


    companion object{
        val logger = LoggerFactory.getLogger(ScheduledTaskService::class.java)
    }
}