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
    @Bean
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
                        GeometryFactory().createPoint(Coordinate(45.02171431907213, 38.96941571781291)),
                    )
                ),
                positionDescription = "ул. Красная 27"
            ),
            Tsodd(
                id = 0L,
                type = firstTsoddType,
                visibility = 1.0,
                condition = tsoddCondition,
                coordinates = GeometryFactory().createGeometryCollection(
                    arrayOf(
                        GeometryFactory().createPoint(Coordinate(45.038732700905676, 38.97514777941537)),
                    )
                ),
                positionDescription = "ул. Красная 127"
            ),
            Tsodd(
                id = 0L,
                type = firstTsoddType,
                visibility = 1.0,
                condition = tsoddCondition,
                coordinates = GeometryFactory().createGeometryCollection(
                    arrayOf(
                        GeometryFactory().createPoint(Coordinate(45.022224982701765, 38.969540003062036)),
                    )
                ),
                positionDescription = "ул. Красная 28"
            ),
            Tsodd(
                id = 0L,
                type = firstTsoddType,
                visibility = 1.0,
                condition = tsoddCondition,
                coordinates = GeometryFactory().createGeometryCollection(
                    arrayOf(
                        GeometryFactory().createPoint(Coordinate(45.022354938909686, 38.969540003062036)),
                    )
                ),
                positionDescription = "ул. Красная 29"
            ),
            Tsodd(
                id = 0L,
                type = secondTsoddType,
                visibility = 0.7,
                condition = null,
                coordinates = GeometryFactory().createGeometryCollection(
                    arrayOf(
                        GeometryFactory().createPoint(Coordinate(45.0189295659401, 38.96847625683479)),
                    )
                ),
                positionDescription = "ул. Красная 17"
            ),
            Tsodd(
                id = 0L,
                type = secondTsoddType,
                visibility = 0.7,
                condition = null,
                coordinates = GeometryFactory().createGeometryCollection(
                    arrayOf(
                        GeometryFactory().createPoint(Coordinate(45.019444238496426, 38.96866863427197)),
                    )
                ),
                positionDescription = "ул. Красная 18"
            ),
            Tsodd(
                id = 0L,
                type = secondTsoddType,
                visibility = 0.7,
                condition = null,
                coordinates = GeometryFactory().createGeometryCollection(
                    arrayOf(
                        GeometryFactory().createPoint(Coordinate(45.02148857488962, 38.96937957239384)),
                    )
                ),
                positionDescription = "ул. Красная 26"
            ),
            Tsodd(
                id = 0L,
                type = secondTsoddType,
                visibility = 0.7,
                condition = null,
                coordinates = GeometryFactory().createGeometryCollection(
                    arrayOf(
                        GeometryFactory().createPoint(Coordinate(45.022802064166505, 38.96969475645368)),
                    )
                ),
                positionDescription = "ул. Красная 30"
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

        val taskTypeListFirst = listOf(
            TaskType(
                id = 0L,
                name = "Ревизия светофора",
                taskGroup = firstTaskGroup,
                timeIntervalInDays = 30,
                durationHours = 1,
                moneyResources = 2.0,
                effectiveness = 4,
                crewList = emptyList(),
            ),
            TaskType(
                id = 0L,
                name = "Осмотр кабельных трасс и мест подключений",
                taskGroup = firstTaskGroup,
                timeIntervalInDays = 20,
                durationHours = 2,
                moneyResources = 4.0,
                effectiveness = 3,
                crewList = emptyList(),
            ),
            TaskType(
                id = 0L,
                name = "Техническое обслуживание светофора",
                taskGroup = firstTaskGroup,
                timeIntervalInDays = 60,
                durationHours = 5,
                moneyResources = 4.0,
                effectiveness = 7,
                crewList = emptyList(),
            ),
        )
        val taskTypeListSecond = listOf(
                TaskType(
                    id = 0L,
                    name = "Выявление неисправностей на предмет механических повреждений",
                    taskGroup = secondTaskGroup,
                    timeIntervalInDays = 30,
                    durationHours = 1,
                    moneyResources = 1.0,
                    effectiveness = 2,
                    crewList = emptyList(),
                ),
        )

        val crewList = listOf(
            Crew(id = 0L, name = "Бригада 1", taskTypeList = taskTypeListFirst),
            Crew(id = 0L, name = "Бригада 2", taskTypeList = taskTypeListFirst),
            Crew(id = 0L, name = "Бригада 3", taskTypeList = taskTypeListSecond),
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

        taskTypeRepository.saveAll(taskTypeListFirst)
        taskTypeRepository.saveAll(taskTypeListSecond)

        crewRepository.saveAll(crewList)
    }

}

fun main(args: Array<String>) {
    runApplication<TsoddServiceApplication>(*args)
}
