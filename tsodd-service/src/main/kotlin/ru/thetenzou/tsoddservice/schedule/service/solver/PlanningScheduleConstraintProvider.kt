package ru.thetenzou.tsoddservice.schedule.service.solver

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.api.score.stream.ConstraintCollectors.sum
import org.optaplanner.core.api.score.stream.ConstraintCollectors.toList
import org.optaplanner.core.api.score.stream.ConstraintFactory
import org.optaplanner.core.api.score.stream.ConstraintProvider
import org.optaplanner.core.api.score.stream.Joiners
import ru.thetenzou.tsoddservice.crew.model.Crew
import ru.thetenzou.tsoddservice.schedule.model.solver.PlannedTask
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

@ConstraintConfiguration
class PlanningScheduleConstraintProvider : ConstraintProvider {
    override fun defineConstraints(constraintFactory: ConstraintFactory) =
        arrayOf(
            assignTask(constraintFactory),
            intervalsLimit(constraintFactory),
            workDayLimit(constraintFactory),
            allCrews(constraintFactory),
            crewLoadBalance(constraintFactory),
            maxEffectiveness(constraintFactory),
        )

    private fun assignTask(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.date == null || task.crew == null }
            .penalize("assign task", HardSoftScore.ofHard(1_000_000))

    private fun intervalsLimit(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .join(
                PlannedTask::class.java,
                Joiners.equal(PlannedTask::tsodd),
                Joiners.equal(PlannedTask::taskType),
            )
            .filter { task1, task2 -> task1.date != null && task2.date != null }
            .reward(
                "interval between tasks",
                HardSoftScore.ONE_SOFT,
                fun(task1, task2): Int {
                    val interval = ChronoUnit.DAYS.between(task1.date, task2.date).toInt()
                    val requiredInterval = task1.taskType?.timeIntervalInDays ?: return 0
                    val delta = (requiredInterval - interval).absoluteValue + 1
                    return 1_000_000 / delta
                }
            )

    private fun workDayLimit(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.crew != null && task.date != null }
            .groupBy(PlannedTask::crew, PlannedTask::date, sum { task -> task.taskType?.durationHours ?: 0 })
            .filter { _, _, totalSum -> totalSum > 8 }
            .penalize("work hour limit", HardSoftScore.ofHard(1_000_000))

    private fun allCrews(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.crew != null && task.date != null }
            .groupBy(PlannedTask::crew)
            .reward("load all crews", HardSoftScore.ofHard(1_000_000))

    private fun crewLoadBalance(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.crew != null && task.date != null }
            .groupBy(PlannedTask::crew, sum { task -> task.taskType?.durationHours ?: 0 })
            .groupBy(fun(_, _): Int { return 1 }, toList(fun(v1, v2): Pair<Crew?, Int> { return v1 to v2 }))
            .reward(
                "crew load balance",
                HardSoftScore.ONE_SOFT,
                fun(_, v2): Int {
                    val maxWorkLoad = v2.maxOf { it.second }
                    val minWorkLoad = v2.minOf { it.second }
                    val delta = (maxWorkLoad - minWorkLoad).absoluteValue + 1
                    return 1_000_000 / delta
                }
            )

    private fun maxEffectiveness(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.crew != null && task.date != null }
            .groupBy(sum { task -> task.taskType?.effectiveness ?: 0 })
            .reward(
                "max effectiveness",
                HardSoftScore.ONE_HARD,
                fun(effectiveness): Int { return 1_000_000 * effectiveness }
            )
}