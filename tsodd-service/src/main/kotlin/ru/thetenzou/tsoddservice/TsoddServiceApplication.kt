package ru.thetenzou.tsoddservice

import org.locationtech.jts.geom.GeometryFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.thetenzou.tsoddservice.crew.model.Crew
import ru.thetenzou.tsoddservice.task.model.Task
import ru.thetenzou.tsoddservice.task.model.TaskGroup
import ru.thetenzou.tsoddservice.tsodd.model.Tsodd
import ru.thetenzou.tsoddservice.tsodd.model.TsoddCondition
import ru.thetenzou.tsoddservice.tsodd.model.TsoddName
import ru.thetenzou.tsoddservice.tsodd.model.TsoddType
import ru.thetenzou.tsoddservice.crew.repository.CrewRepository
import ru.thetenzou.tsoddservice.task.repository.TaskGroupRepository
import ru.thetenzou.tsoddservice.task.repository.TaskRepository
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddConditionRepository
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddNameRepository
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddRepository
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddTypeRepository

@SpringBootApplication
class TsoddServiceApplication(
    private val tsoddTypeRepository: TsoddTypeRepository,
    private val tsoddNameRepository: TsoddNameRepository,
    private val tsoddConditionRepository: TsoddConditionRepository,
    private val tsoddRepository: TsoddRepository,

    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository,

    private val crewRepository: CrewRepository,
) {

    fun initdb() = CommandLineRunner {

        val firstTsoddType = TsoddType(id = 0L, "first type", taskGroup = null)
        val secondTsoddType = TsoddType(id = 0L, "first type", taskGroup = null)

        val firstTsoddName = TsoddName(id = 0L, tsoddType = firstTsoddType, name = "first tsodd name")
        val secondTsoddName = TsoddName(id = 0L, tsoddType = secondTsoddType, name = "second tsodd name")

        val tsoddCondition = TsoddCondition(id = 0L, name = "condition")

        val tsoddList = listOf(
            Tsodd(
                id = 0L,
                name = firstTsoddName,
                visibility = 1.0,
                condition = tsoddCondition,
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

        val firstTaskGroup =
            TaskGroup(id = 0L, name = "first group", tsoddType = listOf(firstTsoddType), tasks = emptyList())
        val secondTaskGroup =
            TaskGroup(id = 0L, name = "first group", tsoddType = listOf(secondTsoddType), tasks = emptyList())

        val taskList = listOf(
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

        val crewList = listOf(
            Crew(
                id = 0L,
                name = "first crew"
            ),
            Crew(
                id = 0L,
                name = "second crew"
            ),
            Crew(
                id = 0L,
                name = "third crew"
            ),
        )
        firstTsoddType.taskGroup = listOf(firstTaskGroup)
        secondTsoddType.taskGroup = listOf(secondTaskGroup)

        taskGroupRepository.save(firstTaskGroup)
        taskGroupRepository.save(secondTaskGroup)

        tsoddTypeRepository.save(firstTsoddType)
        tsoddTypeRepository.save(secondTsoddType)

        tsoddNameRepository.save(firstTsoddName)
        tsoddNameRepository.save(secondTsoddName)

        tsoddConditionRepository.save(tsoddCondition)

        tsoddRepository.saveAll(tsoddList)

        taskRepository.saveAll(taskList)

        crewRepository.saveAll(crewList)
    }

}

fun main(args: Array<String>) {
    runApplication<TsoddServiceApplication>(*args)
}
