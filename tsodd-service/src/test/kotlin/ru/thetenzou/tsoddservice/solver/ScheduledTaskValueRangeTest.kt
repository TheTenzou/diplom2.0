package ru.thetenzou.tsoddservice.solver

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.locationtech.jts.geom.GeometryFactory
import ru.thetenzou.tsoddservice.model.solver.ScheduledTask
import ru.thetenzou.tsoddservice.model.task.Task
import ru.thetenzou.tsoddservice.model.task.TaskGroup
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import ru.thetenzou.tsoddservice.model.solver.Tsodd as SolverTsodd
import ru.thetenzou.tsoddservice.model.tsodd.TsoddName
import ru.thetenzou.tsoddservice.model.tsodd.TsoddType
import java.time.LocalDateTime
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ScheduledTaskValueRangeTest {

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

    private val solverTsoddList = listOf(
        SolverTsodd(tsoddList[0], 1L),
        SolverTsodd(tsoddList[1], 2L)
    )

    private val firstTaskGroup =
        TaskGroup(id = 1L, name = "first group", tsoddType = firstTsoddType, tasks = emptyList())
    private val secondTaskGroup =
        TaskGroup(id = 2L, name = "first group", tsoddType = secondTsoddType, tasks = emptyList())

    private val firstTsoddTypeTaskList = listOf(
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
            name = "forth task",
            taskGroup = firstTaskGroup,
            timeIntervalInDays = 5,
            durationHours = 3,
            moneyResources = 4.0,
            effectiveness = 1,
        ),
    )

    private val secondTsoddTypeTaskList = listOf(
        Task(
            id = 0L,
            name = "third task",
            taskGroup = firstTaskGroup,
            timeIntervalInDays = 5,
            durationHours = 3,
            moneyResources = 4.0,
            effectiveness = 1,
        )
    )


    private fun Any?.toGreenString() = "\u001B[32m${toString()}\u001B[0m"

    private fun ScheduledTask.print() {
        println(
            "tsodd name: ${tsodd.name.name.toGreenString().padStart(30)}; " +
                    "task name: ${task.name.toGreenString().padStart(25)}; " +
                    "date: ${dateTime.year.toGreenString()} ${
                        dateTime.month.toGreenString().padStart(7)
                    } ${dateTime.dayOfMonth.toGreenString().padStart(4)}"
        )
    }

    @BeforeAll
    fun `init data`() {
        firstTsoddType.taskGroup = firstTaskGroup
        secondTsoddType.taskGroup = secondTaskGroup
        firstTaskGroup.tasks = firstTsoddTypeTaskList
        secondTaskGroup.tasks = secondTsoddTypeTaskList
    }

    @Test
    fun `display test`() {
        val scheduledTaskValueRange = ScheduledTaskValueRange(
            taskMap = mapOf(1L to firstTsoddTypeTaskList, 2L to secondTsoddTypeTaskList),
            tsoddList = solverTsoddList,
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now().plusDays(2),
        )
//        println(scheduledTaskValueRange.size)
//        scheduledTaskValueRange.get(0).print()
//        scheduledTaskValueRange.createOriginalIterator().forEach { it.print() }
        scheduledTaskValueRange.createOriginalIterator().forEach { " " }
//        scheduledTaskValueRange.get(6)
//        val randIntegrator = scheduledTaskValueRange.createRandomIterator(Random())
//        for (i in 0..10) {
//            randIntegrator.next().print()
//        }

//        val scheduledTask = ScheduledTask(
//            id = null,
//            schedule = null,
//            dataTime = LocalDateTime.now().plusDays(1),
//            tsodd = tsoddList[0],
//            task = taskList[0],
//            selected = false,
//        )
//
//        println(scheduledTaskValueRange.contains(scheduledTask))
    }
}