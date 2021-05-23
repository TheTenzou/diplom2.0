package ru.thetenzou.tsoddservice.solver

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.api.score.stream.Constraint
import org.optaplanner.core.api.score.stream.ConstraintCollectors.count
import org.optaplanner.core.api.score.stream.ConstraintFactory
import org.optaplanner.core.api.score.stream.ConstraintProvider
import ru.thetenzou.tsoddservice.model.solver.PlanningTask

class PlanningScheduleConstraintProvider : ConstraintProvider {
    override fun defineConstraints(constraintFactory: ConstraintFactory): Array<Constraint> {
        return arrayOf<Constraint>(
            assignCrew(constraintFactory),
            crewLimit(constraintFactory),
            assignDate(constraintFactory),
            sameDay(constraintFactory),
        )
    }

    // TODO update rules

    private fun assignCrew(constraintFactory: ConstraintFactory) =
        constraintFactory.from(PlanningTask::class.java)
            .filter { it.crew != null }
            .reward("assign crew", HardSoftScore.ONE_SOFT)

    private fun crewLimit(constraintFactory: ConstraintFactory) =
        constraintFactory.from(PlanningTask::class.java)
            .groupBy(PlanningTask::crew, count())
            .filter { crew, count -> crew != null && count > 3 }
            .penalize("crew limit", HardSoftScore.ONE_HARD)

    private fun assignDate(constraintFactory: ConstraintFactory) =
        constraintFactory.from(PlanningTask::class.java)
            .filter { task -> task.date != null && task.crew != null }
            .reward("assign crew and date", HardSoftScore.ONE_HARD)

    private fun sameDay(constraintFactory: ConstraintFactory) =
        constraintFactory.from(PlanningTask::class.java)
            .groupBy(PlanningTask::date, count())
            .filter { date, count -> date != null && count > 4 }
            .penalize("penalize same day", HardSoftScore.ONE_HARD)
}