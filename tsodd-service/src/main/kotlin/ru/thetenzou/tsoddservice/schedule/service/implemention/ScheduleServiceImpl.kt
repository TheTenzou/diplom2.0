package ru.thetenzou.tsoddservice.schedule.service.implemention

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.thetenzou.tsoddservice.schedule.dto.ScheduleDto
import ru.thetenzou.tsoddservice.schedule.dto.response.SchedulePageResponse
import ru.thetenzou.tsoddservice.schedule.repository.ScheduleRepository
import ru.thetenzou.tsoddservice.schedule.service.ScheduleService

@Service
class ScheduleServiceImpl(
    private val scheduleRepository: ScheduleRepository,
) : ScheduleService {

    override fun getAllSchedules(pageable: Pageable): SchedulePageResponse {
        val results = scheduleRepository.findAll(pageable)

        return SchedulePageResponse(
            total = results.totalElements,
            page = results.number,
            pageSize = results.size,
            previousPage = if (results.hasPrevious()) results.number - 1 else 0,
            nextPage = if (results.hasNext()) results.number + 1 else 0,
            totalPages = results.totalPages,
            data = results.content.map { ScheduleDto(it) },
        )
    }

}