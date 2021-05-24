package ru.thetenzou.tsoddservice.schedule.service.solver

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.thetenzou.tsoddservice.schedule.model.Schedule
import ru.thetenzou.tsoddservice.schedule.model.solver.TsoddScheduleProblem
import ru.thetenzou.tsoddservice.crew.repository.CrewRepository
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
     * @param startDate starting date of schedule
     * @param endDate ending date of schedule
     */
    fun createNewSchedule(name: String, startDate: LocalDate, endDate: LocalDate): Long {
        logger.info("Create new schedule")

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

    /**
     * getPlanningSchedule init new PlanningSchedule problem base on schedule id
     *
     * @param id schedule id
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

        val tasks = tsoddScheduleProblem.planningTaskList ?: return
        val scheduleId = tsoddScheduleProblem.scheduleId ?: return

        val scheduleOptional = scheduleRepository.findById(scheduleId)
        if (scheduleOptional.isEmpty) {
            return
        }
        val schedule = scheduleOptional.get()

        plannedTaskService.save(schedule, tasks)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TsoddScheduleProblemService::class.java)
    }
}