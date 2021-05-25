package ru.thetenzou.tsoddservice

import org.locationtech.jts.geom.GeometryFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.thetenzou.tsoddservice.crew.model.Crew
import ru.thetenzou.tsoddservice.crew.repository.CrewRepository
import ru.thetenzou.tsoddservice.task.model.TaskGroup
import ru.thetenzou.tsoddservice.task.model.TaskType
import ru.thetenzou.tsoddservice.task.repository.TaskGroupRepository
import ru.thetenzou.tsoddservice.task.repository.TaskTypeRepository
import ru.thetenzou.tsoddservice.tsodd.model.Tsodd
import ru.thetenzou.tsoddservice.tsodd.model.TsoddCondition
import ru.thetenzou.tsoddservice.tsodd.model.TsoddGroup
import ru.thetenzou.tsoddservice.tsodd.model.TsoddType
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddConditionRepository
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddGroupRepository
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddRepository
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddTypeRepository

@SpringBootApplication
class TsoddServiceApplication(
    private val tsoddGroupRepository: TsoddGroupRepository,
    private val tsoddTypeRepository: TsoddTypeRepository,
    private val tsoddConditionRepository: TsoddConditionRepository,
    private val tsoddRepository: TsoddRepository,

    private val taskGroupRepository: TaskGroupRepository,
    private val taskTypeRepository: TaskTypeRepository,

    private val crewRepository: CrewRepository,
) {

    /**
     * generate initial data for tests
     */
    fun initdb() = CommandLineRunner {

        val firstTsoddGroup = TsoddGroup(id = 0L, "first group", taskGroup = null)
        val secondTsoddGroup = TsoddGroup(id = 0L, "first group", taskGroup = null)

        val firstTsoddType = TsoddType(id = 0L, tsoddGroup = firstTsoddGroup, name = "first tsodd type")
        val secondTsoddType = TsoddType(id = 0L, tsoddGroup = secondTsoddGroup, name = "second tsodd type")

        val tsoddCondition = TsoddCondition(id = 0L, name = "condition")

        val tsoddList = listOf(
            Tsodd(
                id = 0L,
                type = firstTsoddType,
                visibility = 1.0,
                condition = tsoddCondition,
                coordinates = GeometryFactory().createGeometryCollection(),
            ),
            Tsodd(
                id = 0L,
                type = secondTsoddType,
                visibility = 0.7,
                condition = null,
                coordinates = GeometryFactory().createGeometryCollection(),
            ),
        )

        val firstTaskGroup =
            TaskGroup(id = 0L, name = "first group", tsoddGroups = listOf(firstTsoddGroup), taskType = emptyList())
        val secondTaskGroup =
            TaskGroup(id = 0L, name = "first group", tsoddGroups = listOf(secondTsoddGroup), taskType = emptyList())

        val taskTypeList = listOf(
            TaskType(
                id = 0L,
                name = "first task type",
                taskGroup = firstTaskGroup,
                timeIntervalInDays = 10,
                durationHours = 2,
                moneyResources = 5.0,
                effectiveness = 2,
            ),
            TaskType(
                id = 0L,
                name = "second task type",
                taskGroup = firstTaskGroup,
                timeIntervalInDays = 5,
                durationHours = 3,
                moneyResources = 4.0,
                effectiveness = 1,
            ),
            TaskType(
                id = 0L,
                name = "third task type",
                taskGroup = firstTaskGroup,
                timeIntervalInDays = 5,
                durationHours = 3,
                moneyResources = 4.0,
                effectiveness = 1,
            ),
            TaskType(
                id = 0L,
                name = "forth task type",
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
        firstTsoddGroup.taskGroup = listOf(firstTaskGroup)
        secondTsoddGroup.taskGroup = listOf(secondTaskGroup)

        taskGroupRepository.save(firstTaskGroup)
        taskGroupRepository.save(secondTaskGroup)

        tsoddGroupRepository.save(firstTsoddGroup)
        tsoddGroupRepository.save(secondTsoddGroup)

        tsoddTypeRepository.save(firstTsoddType)
        tsoddTypeRepository.save(secondTsoddType)

        tsoddConditionRepository.save(tsoddCondition)

        tsoddRepository.saveAll(tsoddList)

        taskTypeRepository.saveAll(taskTypeList)

        crewRepository.saveAll(crewList)
    }

}

fun main(args: Array<String>) {
    runApplication<TsoddServiceApplication>(*args)
}
