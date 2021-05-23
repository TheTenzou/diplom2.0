package ru.thetenzou.tsoddservice.model.solver

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable
import ru.thetenzou.tsoddservice.model.crew.Crew
import ru.thetenzou.tsoddservice.model.task.Task
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import java.time.LocalDate

/**
 * A PlanningTask is task that planned to be performed on given tsodd in given date by given crew
 *
 * @param tsodd target tsodd of this planned task
 * @param task planned task
 * @param date date when task is planned
 * @param crew crew that perform this task
 */
@PlanningEntity
data class PlanningTask(

    var tsodd: Tsodd?,

    var task: Task?,

    @PlanningVariable(valueRangeProviderRefs = ["dateRange"], nullable = true)
    var date: LocalDate?,

    @PlanningVariable(valueRangeProviderRefs = ["crewRange"], nullable = true)
    var crew: Crew?,
) {

    /**
     * default constructor required for OptaPlanner
     */
    constructor() : this(null, null, null, null)
}
