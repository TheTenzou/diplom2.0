package ru.thetenzou.tsoddservice.schedule.service

import ru.thetenzou.tsoddservice.schedule.dto.request.ScheduledTaskRequestDto
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduledTaskDetailDto

/**
 * A ScheduledTaskService provide logic for working with scheduled tasks
 */
interface ScheduledTaskService {

    /**
     * createScheduledTask create new scheduled task
     *
     * @param scheduledTaskDto holds values of created schedule
     *
     * @return created schedule
     */
    fun createScheduledTask(scheduledTaskDto: ScheduledTaskRequestDto): ScheduledTaskDetailDto
}