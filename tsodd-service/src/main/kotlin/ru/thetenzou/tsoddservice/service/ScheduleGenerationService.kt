package ru.thetenzou.tsoddservice.service

import org.optaplanner.core.api.solver.SolverManager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.thetenzou.tsoddservice.model.solver.PlanningSchedule
import java.time.LocalDate

@Service
class ScheduleGenerationService(
    private val solverManager: SolverManager<PlanningSchedule, Long>,
    private val planningScheduleService: PlanningScheduleService,
) {

    fun planSchedule(name: String, startDate: LocalDate, endDate: LocalDate) {
        if (startDate > endDate) {
            throw IllegalArgumentException("start date has to be before end date")
        }

        val scheduleId = planningScheduleService.createNewSchedule(name, startDate, endDate)

        logger.info("Start generating schedule")
        solverManager.solve(
            scheduleId,
            planningScheduleService::getSchedule,
            planningScheduleService::saveSchedule,
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ScheduleGenerationService::class.java)
    }
}