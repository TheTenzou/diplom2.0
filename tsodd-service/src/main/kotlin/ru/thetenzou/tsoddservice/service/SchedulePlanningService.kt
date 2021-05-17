package ru.thetenzou.tsoddservice.service

import org.locationtech.jts.geom.GeometryFactory
import org.optaplanner.core.api.solver.SolverManager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.thetenzou.tsoddservice.model.crew.Crew
import ru.thetenzou.tsoddservice.model.solver.PlanningSchedule
import ru.thetenzou.tsoddservice.model.solver.PlanningTask
import ru.thetenzou.tsoddservice.model.task.Task
import ru.thetenzou.tsoddservice.model.task.TaskGroup
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import ru.thetenzou.tsoddservice.model.tsodd.TsoddName
import ru.thetenzou.tsoddservice.model.tsodd.TsoddType
import java.time.LocalDate

@Service
class SchedulePlanningService(
    private val solverManager: SolverManager<PlanningSchedule, Long>,
    private val planningScheduleService: PlanningScheduleService,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(SchedulePlanningService::class.java)
    }

    fun planSchedule(name: String, startDate: LocalDate, endDate: LocalDate) {
        if (startDate > endDate) {
            throw IllegalArgumentException("start date have to be before end date")
        }

        val scheduleId = planningScheduleService.createNewSchedule(name, startDate, endDate)

        solverManager.solveAndListen(
            scheduleId,
            planningScheduleService::getSchedule,
            planningScheduleService::saveSchedule,
        )
    }

//    private fun getProblem(id: Long) = PlanningSchedule(
//        availableDates = listOf(LocalDate.now(), LocalDate.now().plusDays(1)),
//        availableCrews = crewList,
//        planningTaskList = scheduledTaskList,
//    )
//
//    private fun saveProblem(planningSchedule: PlanningSchedule) {
//        logger.info("new solution")
//        planningSchedule.planningTaskList?.forEach { it.print() }
//    }
//
//    private fun Any?.toGreenString() = "\u001B[32m${toString()}\u001B[0m"
//
//    private fun PlanningTask.print() =
//        logger.info(
//            "tsodd name: ${tsodd?.name?.name.toGreenString().padStart(30)}; " +
//                    "task name: ${task?.name.toGreenString().padStart(25)}; " +
//                    "date: ${date?.year.toGreenString()} ${
//                        date?.month.toGreenString().padStart(7)
//                    } ${date?.dayOfMonth.toGreenString().padStart(4)}; " +
//                    "status: ${crew?.name.toGreenString()}"
//        )
//
}