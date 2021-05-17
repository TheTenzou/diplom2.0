package ru.thetenzou.tsoddservice.model.solver

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.solution.PlanningScore
import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import java.time.LocalDate

@PlanningSolution
class PlanningSchedule(
    @ValueRangeProvider(id = "dateRange")
    val availableDates: List<LocalDate>?,
    @ValueRangeProvider(id = "selectedRange")
    val selected: List<Boolean>?,
    @PlanningEntityCollectionProperty
    val listOfScheduledTask: List<PlanningTask>?,
) {
    @PlanningScore
    lateinit var score: HardSoftScore

    constructor() : this(null, null, null)
}