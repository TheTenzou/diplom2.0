package ru.thetenzou.tsoddservice.schedule.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.schedule.model.Schedule
import ru.thetenzou.tsoddservice.schedule.model.ScheduledTask

@RepositoryRestResource
interface ScheduledTaskRepository : JpaRepository<ScheduledTask, Long> {

    fun getBySchedule(schedule: Schedule): List<ScheduledTask>
}