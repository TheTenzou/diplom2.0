package ru.thetenzou.tsoddservice.model.solver

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable
import ru.thetenzou.tsoddservice.model.crew.Crew
import ru.thetenzou.tsoddservice.model.task.Task
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import java.time.LocalDate

@PlanningEntity
data class PlanningTask(
    var tsodd: Tsodd?,
    var task: Task?,
    @PlanningVariable(valueRangeProviderRefs = ["dateRange"], nullable = true)
    var date: LocalDate?,
    @PlanningVariable(valueRangeProviderRefs = ["crewRange"], nullable = true)
    var crew: Crew?,
) {
    constructor() : this(null, null, null, null)
}
