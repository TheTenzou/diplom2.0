package ru.thetenzou.tsoddservice.schedule.service.implemention

import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDetailDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDto
import ru.thetenzou.tsoddservice.schedule.repository.ScheduleRepository
import ru.thetenzou.tsoddservice.schedule.repository.ScheduledTaskRepository
import ru.thetenzou.tsoddservice.schedule.service.ScheduleService
import ru.thetenzou.tsoddservice.util.PagedResponse

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

    override fun getScheduleById(id: Long): ScheduleDetailDto {
        val result = scheduleRepository.findById(id)

        if (result.isEmpty) {
            throw IllegalArgumentException("id does not exist")
        }

        val schedule = result.get()

        val scheduledTaskList = scheduledTaskRepository.getBySchedule(schedule)

        return ScheduleDetailDto(
            schedule = schedule,
            tasks = scheduledTaskList,
        )
    }

    companion object {
        val logger = LoggerFactory.getLogger(ScheduleServiceImpl::class.java)
    }
}