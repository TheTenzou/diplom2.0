package ru.thetenzou.tsoddservice.model.solver

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningScore
import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import ru.thetenzou.tsoddservice.model.crew.Crew
import java.time.LocalDate

@PlanningSolution
class PlanningSchedule(
    val id: Long?,
    @ValueRangeProvider(id = "dateRange")
    val availableDates: List<LocalDate>?,
    @ValueRangeProvider(id = "crewRange")
    val availableCrews: List<Crew>?,
    @PlanningEntityCollectionProperty
    val planningTaskList: List<PlanningTask>?,
) {
    @PlanningScore
    lateinit var score: HardSoftScore

    constructor() : this(null, null, null, null)
}