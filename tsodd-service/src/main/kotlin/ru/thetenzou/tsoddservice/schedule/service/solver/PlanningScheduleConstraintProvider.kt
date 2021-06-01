package ru.thetenzou.tsoddservice.schedule.service.solver

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore
import org.optaplanner.core.api.score.stream.ConstraintCollectors.*
import org.optaplanner.core.api.score.stream.ConstraintFactory
import org.optaplanner.core.api.score.stream.ConstraintProvider
import org.optaplanner.core.api.score.stream.Joiners
import ru.thetenzou.tsoddservice.crew.model.Crew
import ru.thetenzou.tsoddservice.schedule.model.solver.PlannedTask
import ru.thetenzou.tsoddservice.schedule.model.solver.ScheduleParameters
import java.math.BigDecimal
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
            resourceLimit(constraintFactory),
            minDistance(constraintFactory),
            crewSkills(constraintFactory),
            limitAmountOfDays(constraintFactory),
        )

    private fun assignTask(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.date == null || task.crew == null }
            .penalize("assign task", HardMediumSoftScore.ofMedium(1_000_000))

    private fun intervalsLimit(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.date != null && task.crew != null }
            .groupBy(PlannedTask::taskType, PlannedTask::tsodd, toList(PlannedTask::date))
            .reward(
                "interval between tasks",
                HardMediumSoftScore.ONE_MEDIUM,
                fun(taskType, _, dateList): Int {
                    dateList.sortBy { it }
                    val intervalCount = dateList.size - 1
                    if (intervalCount == 0) {
                        return 1_000_000
                    }

                    val requiredInterval = taskType?.timeIntervalInDays ?: return 0
                    var averageDelta = 0
                    for (i in 0 until intervalCount) {
                        val interval = ChronoUnit.DAYS.between(dateList[i], dateList[i + 1]).toInt()
                        val delta = (requiredInterval - interval).absoluteValue + 1
                        averageDelta += delta
                    }
                    averageDelta /= intervalCount
                    return 1_000_000 / averageDelta

                }
            )

    private fun workDayLimit(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.crew != null && task.date != null }
            .groupBy(PlannedTask::crew, PlannedTask::date, sum { task -> task.taskType?.durationHours ?: 0 })
            .filter { _, _, totalSum -> totalSum > 8 }
            .penalize("work hour limit", HardMediumSoftScore.ONE_HARD)

    private fun allCrews(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.crew != null && task.date != null }
            .groupBy(PlannedTask::crew)
            .reward("load all crews", HardMediumSoftScore.ofMedium(1_000_000))

    private fun crewLoadBalance(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.crew != null && task.date != null }
            .groupBy(PlannedTask::crew, sum { task -> task.taskType?.durationHours ?: 0 })
            .groupBy(fun(_, _): Int { return 1 }, toList(fun(v1, v2): Pair<Crew?, Int> { return v1 to v2 }))
            .reward(
                "crew load balance",
                HardMediumSoftScore.ONE_SOFT,
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
                HardMediumSoftScore.ONE_MEDIUM,
                fun(effectiveness): Int { return 1_000_000 * effectiveness }
            )

    private fun resourceLimit(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.crew != null && task.date != null }
            .groupBy(sumBigDecimal { task -> BigDecimal.valueOf(task.taskType?.moneyResources ?: 0.0) })
            .join(ScheduleParameters::class.java)
            .penalize(
                "resourceLimit",
                HardMediumSoftScore.ONE_HARD,
                fun(totalResources, resourceLimit): Int {
                    if (totalResources > BigDecimal.valueOf(resourceLimit.resourceLimit)) {
                        return 1
                    }
                    return 0
                },
            )

    private fun minDistance(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .join(
                PlannedTask::class.java,
                Joiners.equal(PlannedTask::crew),
                Joiners.equal(PlannedTask::date)
            )
            .penalize(
                "minimize distance",
                HardMediumSoftScore.ONE_SOFT,
                fun(task1, task2):Int {
                    val point1 = task1.tsodd?.coordinates?.interiorPoint
                    val point2 = task2.tsodd?.coordinates?.interiorPoint
                    val distance = point1?.distance(point2) ?: return 1_000_000
                    return (1_000_000 / distance).toInt()
                }
            )

    private fun crewSkills(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> !(task.crew?.taskTypeList?.contains(task.taskType) ?: false) }
            .penalize("crew task skill limit", HardMediumSoftScore.ONE_HARD)

    private fun limitAmountOfDays(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.date != null && task.crew != null }
            .groupBy(PlannedTask::date)
            .penalize("limit amount of days", HardMediumSoftScore.ofSoft(1_000_000))
}