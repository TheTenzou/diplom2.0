package ru.thetenzou.tsoddservice.schedule.service.implemention

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDetailDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduledTaskDto
import ru.thetenzou.tsoddservice.schedule.repository.ScheduleRepository
import ru.thetenzou.tsoddservice.schedule.repository.ScheduledTaskRepository
import ru.thetenzou.tsoddservice.schedule.service.ScheduleService
import ru.thetenzou.tsoddservice.common.dto.PagedResponse
import ru.thetenzou.tsoddservice.schedule.dto.request.ScheduleRequestDto
import ru.thetenzou.tsoddservice.schedule.model.Schedule
import ru.thetenzou.tsoddservice.schedule.model.ScheduleStatus
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@Service
class ScheduleServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    private val scheduledTaskRepository: ScheduledTaskRepository,
) : ScheduleService {

    override fun getAllSchedules(page: Int, size: Int): PagedResponse<ScheduleDto> {
        val paging = PageRequest.of(page, size)

        val pagedSchedules = scheduleRepository.findAll(paging)

        val pagedSchedulesDto = pagedSchedules.map { schedule -> ScheduleDto(schedule) }

        logger.info("Fetched ${pagedSchedulesDto.totalElements} schedules")
        return PagedResponse(pagedSchedulesDto)
    }

    override fun getScheduleById(id: Long, page: Int, size: Int): ScheduleDetailDto {
        val result = scheduleRepository.findById(id)

        if (result.isEmpty) {
            throw EntityNotFoundException("Schedule with id: $id does not exist")
        }

        val schedule = result.get()

        val paging = PageRequest.of(page, size, Sort.by("date"))

        val scheduledTaskPage = scheduledTaskRepository.getBySchedule(schedule, paging)

        val scheduledTaskDtoPage = scheduledTaskPage.map { ScheduledTaskDto(it) }

        logger.info("Fetched detail info about schedule with id: ${schedule.id}")
        return ScheduleDetailDto(
            schedule = schedule,
            tasks = scheduledTaskDtoPage,
        )
    }

    override fun createSchedule(scheduleRequest: ScheduleRequestDto): ScheduleDto {
        if (scheduleRequest.name == null) {
            throw IllegalArgumentException("Field name can't be null")
        }
        if (scheduleRequest.startDate == null) {
            throw IllegalArgumentException("Field startDate can't be null")
        }
        if (scheduleRequest.endDate == null) {
            throw IllegalArgumentException("Field endDate can't be null")
        }

        val newSchedule = Schedule(
            id = 0L,
            name = scheduleRequest.name,
            createdDate = LocalDateTime.now(),
            resourceLimit = scheduleRequest.resourceLimit,
            totalResources = 0.0,
            startDate = scheduleRequest.startDate,
            endDate = scheduleRequest.endDate,
            status = ScheduleStatus.GENERATED,
            scheduledTask = null,
        )
        val savedSchedule = scheduleRepository.save(newSchedule)

        logger.info("Schedule has been created with id: ${savedSchedule.id} and name: ${savedSchedule.name}")

        return ScheduleDto(savedSchedule)
    }

    override fun updateSchedule(scheduleRequest: ScheduleRequestDto): ScheduleDto {

        val result = scheduleRepository.findById(scheduleRequest.id)

        if (result.isEmpty) {
            throw EntityNotFoundException("schedule with id: ${scheduleRequest.id} doesn't exist")
        }
        val schedule = result.get()
        if (scheduleRequest.name != null) {
            schedule.name = scheduleRequest.name
        }
        if (scheduleRequest.startDate != null) {
            schedule.startDate = scheduleRequest.startDate
        }
        if (scheduleRequest.endDate != null) {
            schedule.endDate = scheduleRequest.endDate
        }

        val savedSchedule = scheduleRepository.save(schedule)

        logger.info("Schedule has been updated with id: ${savedSchedule.id} and name: ${savedSchedule.name}")

        return ScheduleDto(savedSchedule)
    }

    override fun deleteSchedule(id: Long): ScheduleDto {
        val results = scheduleRepository.findById(id)

        if (results.isEmpty) {
            throw EntityNotFoundException("schedule with id: $id doesn't exist")
        }
        val schedule = results.get()

        scheduleRepository.delete(schedule)

        logger.info("Schedule with id: $id has been deleted")

        return ScheduleDto(schedule)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ScheduleServiceImpl::class.java)
    }
}