package ru.thetenzou.tsoddservice.schedule.service.implemention

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
import java.time.LocalDate
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

        return ScheduleDetailDto(
            schedule = schedule,
            tasks = scheduledTaskDtoPage,
        )
    }

    override fun createSchedule(name: String, startDate: LocalDate, endDate: LocalDate): ScheduleDetailDto {
        val newSchedule = Schedule(
            id = 0L,
            name = name,
            createdDate = LocalDateTime.now(),
            startDate = startDate,
            endDate = endDate,
            scheduledTask = null,
        )
        val savedSchedule = scheduleRepository.save(newSchedule)

        return ScheduleDetailDto(savedSchedule, Page.empty())
    }

    companion object {
        val logger = LoggerFactory.getLogger(ScheduleServiceImpl::class.java)
    }
}