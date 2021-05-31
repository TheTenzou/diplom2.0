package ru.thetenzou.tsoddservice

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
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

        val firstTsoddGroup = TsoddGroup(id = 0L, "дорожные светофоры", taskGroup = null)
        val secondTsoddGroup = TsoddGroup(id = 0L, "дорожные знаки", taskGroup = null)
        val thirdTsoddGroup = TsoddGroup(id = 0L, "дорожная разметка", taskGroup = null)

        val firstTsoddType = TsoddType(id = 0L, tsoddGroup = firstTsoddGroup, name = "2.1")
        val secondTsoddType = TsoddType(id = 0L, tsoddGroup = secondTsoddGroup, name = "1.3")

        val tsoddCondition = TsoddCondition(id = 0L, name = "хорошее")

        val tsoddList = listOf(
            Tsodd(
                id = 0L,
                type = firstTsoddType,
                visibility = 1.0,
                condition = tsoddCondition,
                coordinates = GeometryFactory().createGeometryCollection(
                    arrayOf(
                        GeometryFactory().createPoint(Coordinate(1.0, 1.0)),
                        GeometryFactory().createPoint(Coordinate(1.0, 2.0)),
                    )
                ),
                positionDescription = "ул. Красная 27"
            ),
            Tsodd(
                id = 0L,
                type = secondTsoddType,
                visibility = 0.7,
                condition = null,
                coordinates = GeometryFactory().createGeometryCollection(
                    arrayOf(
                        GeometryFactory().createPoint(Coordinate(3.0, 3.0)),
                    )
                ),
                positionDescription = "ул. Красная 17"
            ),
        )

        val firstTaskGroup =
            TaskGroup(
                id = 0L,
                name = "Обслуживание светофоров",
                tsoddGroups = listOf(firstTsoddGroup),
                taskType = emptyList()
            )
        val secondTaskGroup =
            TaskGroup(
                id = 0L,
                name = "Обслуживание знаков",
                tsoddGroups = listOf(secondTsoddGroup),
                taskType = emptyList()
            )

        val taskTypeList = listOf(
            TaskType(
                id = 0L,
                name = "Ревизия светофора",
                taskGroup = firstTaskGroup,
                timeIntervalInDays = 30,
                durationHours = 1,
                moneyResources = 2.0,
                effectiveness = 4,
            ),
            TaskType(
                id = 0L,
                name = "Осмотр кабельных трасс и мест подключений",
                taskGroup = firstTaskGroup,
                timeIntervalInDays = 20,
                durationHours = 2,
                moneyResources = 4.0,
                effectiveness = 3,
            ),
            TaskType(
                id = 0L,
                name = "Техническое обслуживание светофора",
                taskGroup = firstTaskGroup,
                timeIntervalInDays = 60,
                durationHours = 5,
                moneyResources = 4.0,
                effectiveness = 7,
            ),
            TaskType(
                id = 0L,
                name = "Выявление неисправностей на предмет механических повреждений",
                taskGroup = secondTaskGroup,
                timeIntervalInDays = 30,
                durationHours = 1,
                moneyResources = 1.0,
                effectiveness = 2,
            ),
        )

        val crewList = listOf(
            Crew(id = 0L, name = "Бригада 1"),
            Crew(id = 0L, name = "Бригада 2"),
            Crew(id = 0L, name = "Бригада 3"),
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
