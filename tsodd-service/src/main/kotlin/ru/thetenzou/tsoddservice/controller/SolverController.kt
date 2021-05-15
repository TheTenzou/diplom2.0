package ru.thetenzou.tsoddservice.controller

import org.locationtech.jts.geom.GeometryFactory
import org.optaplanner.core.api.score.ScoreManager
import org.optaplanner.core.api.solver.SolverManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.thetenzou.tsoddservice.model.schedule.ScheduledTask
import ru.thetenzou.tsoddservice.model.task.Task
import ru.thetenzou.tsoddservice.model.task.TaskGroup
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import ru.thetenzou.tsoddservice.model.tsodd.TsoddName
import ru.thetenzou.tsoddservice.model.tsodd.TsoddType
import ru.thetenzou.tsoddservice.solver.PlanningSchedule
import java.time.LocalDate


@RestController
@RequestMapping("/api/tsodd/v1")
class SolverController {

    @Autowired
    private lateinit var solverManager: SolverManager<PlanningSchedule, Long>

    @Autowired
    private lateinit var scoreManager: ScoreManager<PlanningSchedule>


    @GetMapping("/solve")
    fun solve(): String {
        solverManager.solveAndListen(
            1L,
            ::getProblem,
            ::saveProblem,
        )
        return "look at console"
    }


    private val firstTsoddType = TsoddType(id = 0L, "first type", taskGroup = null)
    private val secondTsoddType = TsoddType(id = 0L, "first type", taskGroup = null)
    private val firstTsoddName = TsoddName(id = 0L, tsoddType = firstTsoddType, name = "first tsodd name")
    private val secondTsoddName = TsoddName(id = 0L, tsoddType = secondTsoddType, name = "second tsodd name")

    private val tsoddList = listOf(
        Tsodd(
            id = 0L,
            name = firstTsoddName,
            visibility = 1.0,
            condition = null,
            coordinates = GeometryFactory().createGeometryCollection(),
        ),
        Tsodd(
            id = 0L,
            name = secondTsoddName,
            visibility = 0.7,
            condition = null,
            coordinates = GeometryFactory().createGeometryCollection(),
        ),
    )

    private val firstTaskGroup =
        TaskGroup(id = 1L, name = "first group", tsoddType = firstTsoddType, tasks = emptyList())
    private val secondTaskGroup =
        TaskGroup(id = 2L, name = "first group", tsoddType = secondTsoddType, tasks = emptyList())

    private val taskList = listOf(
        Task(
            id = 0L,
            name = "first task",
            taskGroup = firstTaskGroup,
            timeIntervalInDays = 10,
            durationHours = 2,
            moneyResources = 5.0,
            effectiveness = 2,
        ),
        Task(
            id = 0L,
            name = "second task",
            taskGroup = firstTaskGroup,
            timeIntervalInDays = 5,
            durationHours = 3,
            moneyResources = 4.0,
            effectiveness = 1,
        ),
        Task(
            id = 0L,
            name = "third task",
            taskGroup = firstTaskGroup,
            timeIntervalInDays = 5,
            durationHours = 3,
            moneyResources = 4.0,
            effectiveness = 1,
        ),
        Task(
            id = 0L,
            name = "forth task",
            taskGroup = secondTaskGroup,
            timeIntervalInDays = 5,
            durationHours = 3,
            moneyResources = 4.0,
            effectiveness = 1,
        ),
    )



    private fun getProblem(id: Long) = PlanningSchedule(
        availableDates = listOf(LocalDate.now(), LocalDate.now().plusDays(1)),
        selected = listOf(false, true),
        listOfScheduledTask = listOf(
            ScheduledTask(
                id = null,
                schedule = null,
                date = null,
                tsodd = tsoddList[0],
                task = taskList[0],
                selected = null,
            ),
            ScheduledTask(
                id = null,
                schedule = null,
                date = null,
                tsodd = tsoddList[0],
                task = taskList[0],
                selected = null,
            ),
            ScheduledTask(
                id = null,
                schedule = null,
                date = null,
                tsodd = tsoddList[0],
                task = taskList[1],
                selected = null,
            ),
            ScheduledTask(
                id = null,
                schedule = null,
                date = null,
                tsodd = tsoddList[0],
                task = taskList[2],
                selected = null,
            ),
            ScheduledTask(
                id = null,
                schedule = null,
                date = null,
                tsodd = tsoddList[1],
                task = taskList[3],
                selected = null,
            ),
            ScheduledTask(
                id = null,
                schedule = null,
                date = null,
                tsodd = tsoddList[1],
                task = taskList[3],
                selected = null,
            ),
        )
    )

    private fun Any?.toGreenString() = "\u001B[32m${toString()}\u001B[0m"

    private fun ScheduledTask.print() = println(
        "tsodd name: ${tsodd.name.name.toGreenString().padStart(30)}; " +
                "task name: ${task.name.toGreenString().padStart(25)}; " +
                "date: ${date?.year.toGreenString()} ${
                    date?.month.toGreenString().padStart(7)
                } ${date?.dayOfMonth.toGreenString().padStart(4)}; " +
                "status: $selected"
    )

    private fun saveProblem(planningSchedule: PlanningSchedule) {
        planningSchedule.listOfScheduledTask?.forEach { it.print() }
    }

}