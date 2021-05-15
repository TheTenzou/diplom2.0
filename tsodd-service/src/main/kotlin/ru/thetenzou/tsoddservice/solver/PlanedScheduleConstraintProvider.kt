package ru.thetenzou.tsoddservice.solver

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.api.score.stream.Constraint
import org.optaplanner.core.api.score.stream.ConstraintFactory
import org.optaplanner.core.api.score.stream.ConstraintProvider
import ru.thetenzou.tsoddservice.model.schedule.ScheduledTask

class PlanedScheduleConstraintProvider : ConstraintProvider {
    override fun defineConstraints(constraintFactory: ConstraintFactory): Array<Constraint> {
        return arrayOf<Constraint>(
            selected(constraintFactory)
        )
    }

    private fun selected(constraintFactory: ConstraintFactory): Constraint {
        return constraintFactory.from(ScheduledTask::class.java)
            .filter { it.selected == true }
            .reward("selected", HardSoftScore.ONE_SOFT)
    }
}