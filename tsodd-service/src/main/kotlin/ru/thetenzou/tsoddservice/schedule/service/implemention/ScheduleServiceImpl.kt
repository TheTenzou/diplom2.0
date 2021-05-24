package ru.thetenzou.tsoddservice.schedule.service.implemention

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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
import java.time.LocalDateTime

@Service
class ScheduleServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    private val scheduledTaskRepository: ScheduledTaskRepository,
) : ScheduleService {

    override fun getAllSchedules(page: Int, size: Int): PagedResponse<ScheduleDto> {
        val paging = PageRequest.of(page, size)

        val pagedSchedules = scheduleRepository.findAll(paging)

        val pagedSchedulesDto = pagedSchedules.map { schedule -> ScheduleDto(schedule) }

        logger.info("fetched ${pagedSchedulesDto.size} schedules")
        return PagedResponse(pagedSchedulesDto)
    }

    override fun getScheduleById(id: Long, page: Int, size: Int): ScheduleDetailDto {
        val result = scheduleRepository.findById(id)

        if (result.isEmpty) {
            throw IllegalArgumentException("id does not exist")
        }

        val schedule = result.get()

        val paging = PageRequest.of(page, size)

        val scheduledTaskPage = scheduledTaskRepository.getBySchedule(schedule, paging)

        val scheduledTaskDtoPage = scheduledTaskPage.map { ScheduledTaskDto(it) }

        logger.info("fetched detail info about schedule with id: ${schedule.id}")
        return ScheduleDetailDto(
            schedule = schedule,
            tasks = scheduledTaskDtoPage,
        )
    }

    override fun createSchedule(scheduleRequest: ScheduleRequestDto): ScheduleDetailDto {
        if (scheduleRequest.name == null || scheduleRequest.startDate == null || scheduleRequest.endDate == null) {
            throw IllegalArgumentException("null field are not allowed")
        }
        val newSchedule = Schedule(
            id = 0L,
            name = scheduleRequest.name!!,
            createdDate = LocalDateTime.now(),
            startDate = scheduleRequest.startDate!!,
            endDate = scheduleRequest.endDate!!,
            scheduledTask = null,
        )
        val savedSchedule = scheduleRepository.save(newSchedule)

        logger.info("schedule has been created with id: ${savedSchedule.id} and name: ${savedSchedule.name}")
        return ScheduleDetailDto(savedSchedule, Page.empty())
    }

    override fun updateSchedule(scheduleRequest: ScheduleRequestDto): ScheduleDetailDto {

        val result = scheduleRepository.findById(scheduleRequest.id)

        if (result.isEmpty) {
            throw IllegalArgumentException("schedule id: ${scheduleRequest.id} doesn't exist")
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

        logger.info("schedule has been updated with id: ${savedSchedule.id} and name: ${savedSchedule.name}")
        return ScheduleDetailDto(savedSchedule, Page.empty())
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ScheduleServiceImpl::class.java)
    }
}