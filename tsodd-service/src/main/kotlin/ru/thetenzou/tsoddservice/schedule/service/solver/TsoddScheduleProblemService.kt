package ru.thetenzou.tsoddservice.schedule.service.solver

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.thetenzou.tsoddservice.crew.repository.CrewRepository
import ru.thetenzou.tsoddservice.schedule.model.Schedule
import ru.thetenzou.tsoddservice.schedule.model.ScheduleStatus
import ru.thetenzou.tsoddservice.schedule.model.solver.PlannedTask
import ru.thetenzou.tsoddservice.schedule.model.solver.ScheduleParameters
import ru.thetenzou.tsoddservice.schedule.model.solver.TsoddScheduleProblem
import ru.thetenzou.tsoddservice.schedule.repository.ScheduleRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors

/**
 * TsoddScheduleProblemService manage tsodd schedule planning problem in the process of planning
 */
@Service
@Transactional
class TsoddScheduleProblemService(
    private val scheduleRepository: ScheduleRepository,
    private val crewRepository: CrewRepository,

    private val plannedTaskService: PlannedTaskService,
) {

    /**
     * createNewSchedule create and save new schedule empty to database
     *
     * @param name name of new schedule
     * @param resourceLimit max limit of resources
     * @param startDate starting date of schedule
     * @param endDate ending date of schedule
     *
     * @return created schedule
     */
    fun createNewSchedule(name: String, resourceLimit:Double, startDate: LocalDate, endDate: LocalDate): Schedule {
        logger.info("Create new schedule")

        val schedule = Schedule(
            id = 0L,
            name = name,
            createdDate = LocalDateTime.now(),
            resourceLimit = resourceLimit,
            startDate = startDate,
            endDate = endDate,
            status = ScheduleStatus.GENERATING,
            scheduledTask = null,
        )

        return scheduleRepository.save(schedule)
    }

    /**
     * getPlanningSchedule init new PlanningSchedule problem base on schedule id
     *
     * @param id schedule id
     *
     * @return new problem with initial values
     */
    fun getPlanningSchedule(id: Long): TsoddScheduleProblem {
        val scheduleOptional = scheduleRepository.findById(id)

        if (scheduleOptional.isEmpty) {
            throw IllegalArgumentException()
        }

        val schedule = scheduleOptional.get()

        val availableDates = schedule.startDate.datesUntil(schedule.endDate).collect(Collectors.toList())

        val crews = crewRepository.findAll()

        val days = ChronoUnit.DAYS.between(schedule.startDate, schedule.endDate)

        return TsoddScheduleProblem(
            scheduleId = schedule.id,
            parameters = ScheduleParameters(schedule.resourceLimit),
            availableDates = availableDates,
            availableCrews = crews,
            planningTaskList = plannedTaskService.getPlanningTasks(days)
        )
    }

    /**
     * savePlanningSchedule save solved schedule problem to database
     *
     * @param tsoddScheduleProblem - schedule problem
     */
    fun savePlanningSchedule(tsoddScheduleProblem: TsoddScheduleProblem) {
        logger.info("New scheduled has been saved")

        // tsoddScheduleProblem.planningTaskList?.forEach { logger.info(it.toNiceString()) }

        val tasks = tsoddScheduleProblem.planningTaskList ?: return
        val scheduleId = tsoddScheduleProblem.scheduleId ?: return

        val scheduleOptional = scheduleRepository.findById(scheduleId)
        if (scheduleOptional.isEmpty) {
            return
        }
        val schedule = scheduleOptional.get()

        plannedTaskService.save(schedule, tasks)

        schedule.status = ScheduleStatus.GENERATED

        scheduleRepository.save(schedule)
    }

    private fun Any?.toGreenString() = "\u001B[32m${toString()}\u001B[0m"

    private fun PlannedTask.toNiceString() =
        "tsodd name: ${tsodd?.type?.name.toGreenString().padStart(30)}; " +
                "task name: ${taskType?.name.toGreenString().padStart(25)}; " +
                "duration: ${taskType?.durationHours.toGreenString().padStart(3)}; " +
                "resources: ${taskType?.moneyResources.toGreenString().padStart(4)}; " +
                "date: ${date?.year.toGreenString()} " +
                    "${date?.month.toGreenString().padStart(7)} " +
                    "${date?.dayOfMonth.toGreenString().padStart(4)}; " +
                "crew: ${crew?.name.toGreenString()}"


    companion object {
        val logger: Logger = LoggerFactory.getLogger(TsoddScheduleProblemService::class.java)
    }
}