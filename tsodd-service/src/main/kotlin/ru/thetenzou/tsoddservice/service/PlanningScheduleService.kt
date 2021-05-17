package ru.thetenzou.tsoddservice.service

import org.springframework.stereotype.Service
import ru.thetenzou.tsoddservice.model.schedule.Schedule
import ru.thetenzou.tsoddservice.model.schedule.ScheduledTask
import ru.thetenzou.tsoddservice.model.solver.PlanningSchedule
import ru.thetenzou.tsoddservice.repository.crew.CrewRepository
import ru.thetenzou.tsoddservice.repository.schedule.ScheduleRepository
import ru.thetenzou.tsoddservice.repository.schedule.ScheduledTaskRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors

@Service
class PlanningScheduleService(
    private val scheduleRepository: ScheduleRepository,
    private val scheduledTaskRepository: ScheduledTaskRepository,
    private val crewRepository: CrewRepository,

    private val planningTaskService: PlanningTaskService,
) {

    fun createNewSchedule(name: String, startDate: LocalDate, endDate: LocalDate): Long {
        val schedule = Schedule(
            id = 0L,
            name = name,
            createdDate = LocalDateTime.now(),
            startDate = startDate,
            endDate = endDate,
            scheduledTask = null,
        )

        val savedSchedule = scheduleRepository.save(schedule)

        return savedSchedule.id
    }

    fun getSchedule(id: Long): PlanningSchedule {
        val scheduleOptional = scheduleRepository.findById(id)

        if (scheduleOptional.isEmpty) {
            throw IllegalArgumentException()
        }

        val schedule = scheduleOptional.get()

        val availableDates = schedule.startDate.datesUntil(schedule.endDate).collect(Collectors.toList())

        val crews = crewRepository.findAll()

        val days = ChronoUnit.DAYS.between(schedule.startDate, schedule.endDate)

        return PlanningSchedule(
            id = schedule.id,
            availableDates = availableDates,
            availableCrews = crews,
            planningTaskList = planningTaskService.getPlanningTasks(days)
        )
    }

    fun saveSchedule(planningSchedule: PlanningSchedule) {
        val tasks = planningSchedule.planningTaskList ?: return
        val scheduleId = planningSchedule.id ?: return

        val scheduleOptional = scheduleRepository.findById(scheduleId)
        if (scheduleOptional.isEmpty) {
            return
        }
        val schedule = scheduleOptional.get()

        val validTasks = tasks.filter { it.date != null && it.crew != null }

        val scheduledTaskList =
            validTasks.map { ScheduledTask(id = 0L, schedule, it.date, it.tsodd!!, it.task!!, it.crew!!) }

        scheduledTaskRepository.saveAll(scheduledTaskList)
    }
}