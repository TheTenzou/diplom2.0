package ru.thetenzou.tsoddservice.solver

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.api.score.stream.Constraint
import org.optaplanner.core.api.score.stream.ConstraintCollectors.count
import org.optaplanner.core.api.score.stream.ConstraintFactory
import org.optaplanner.core.api.score.stream.ConstraintProvider
import ru.thetenzou.tsoddservice.model.schedule.ScheduledTask

class PlanningScheduleConstraintProvider : ConstraintProvider {
    override fun defineConstraints(constraintFactory: ConstraintFactory): Array<Constraint> {
        return arrayOf<Constraint>(
            selected(constraintFactory),
            selectedCount(constraintFactory),
        )
    }

    private fun selected(constraintFactory: ConstraintFactory): Constraint {
        return constraintFactory.from(PlanningTask::class.java)
            .filter { it.selected == true }
            .reward("selected", HardSoftScore.ONE_SOFT)
    }

    private fun selectedCount(constraintFactory: ConstraintFactory): Constraint {
        return constraintFactory.from(PlanningTask::class.java)
            .groupBy(PlanningTask::selected, count())
            .filter { selected, count -> selected!! && count > 3 }
            .penalize("selected count constraint", HardSoftScore.ONE_HARD)
    }
}