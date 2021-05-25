package ru.thetenzou.tsoddservice.schedule.service.solver

import org.optaplanner.core.api.solver.SolverManager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.thetenzou.tsoddservice.schedule.dto.response.ScheduleDto
import ru.thetenzou.tsoddservice.schedule.model.solver.TsoddScheduleProblem
import java.time.LocalDate

/**
 * A ScheduleGenerator generate new schedules asynchronously
 *
 * @param solverManager OptaPlanner solver for solving multiple problems
 * @param tsoddScheduleProblemService service for managing PlanningSchedules
 */
@Service
class ScheduleGenerator(
    private val solverManager: SolverManager<TsoddScheduleProblem, Long>,
    private val tsoddScheduleProblemService: TsoddScheduleProblemService,
) {

    /**
     * generateSchedule generate new schedule using solverManager and saves it
     *
     * @param name name of new schedule
     * @param startDate starting date of schedule
     * @param endDate ending date of schedule
     *
     * @return schedule generating schedule
     */
    fun generateSchedule(name: String, startDate: LocalDate, endDate: LocalDate): ScheduleDto {
        if (startDate > endDate) {
            throw IllegalArgumentException("start date has to be before end date")
        }

        val schedule = tsoddScheduleProblemService.createNewSchedule(name, startDate, endDate)

        logger.info("Start generating schedule")
        solverManager.solve(
            schedule.id,
            tsoddScheduleProblemService::getPlanningSchedule,
            tsoddScheduleProblemService::savePlanningSchedule,
        )

        return ScheduleDto(schedule)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ScheduleGenerator::class.java)
    }
}