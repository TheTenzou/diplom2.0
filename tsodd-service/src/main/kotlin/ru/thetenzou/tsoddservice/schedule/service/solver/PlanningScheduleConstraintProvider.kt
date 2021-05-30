package ru.thetenzou.tsoddservice.schedule.service.solver

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.api.score.stream.ConstraintCollectors.*
import org.optaplanner.core.api.score.stream.ConstraintFactory
import org.optaplanner.core.api.score.stream.ConstraintProvider
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
        )

    private fun assignTask(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.date == null || task.crew == null }
            .penalize("assign task", HardSoftScore.ofHard(1_000_000))

    private fun intervalsLimit(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.date != null && task.crew != null }
            .groupBy(PlannedTask::taskType, PlannedTask::tsodd, toList(PlannedTask::date))
            .reward(
                "interval between tasks",
                HardSoftScore.ONE_HARD,
                fun(taskType, v, dateList): Int {
                    dateList.sortBy { it }
                    val intervalCount = dateList.size -1
                    if (intervalCount == 0) {
                        return 1_000_000
                    }

                    val requiredInterval = taskType?.timeIntervalInDays ?: return 0
                    var averageDelta = 0
                    for(i in 0 until intervalCount) {
                        val interval = ChronoUnit.DAYS.between(dateList[i], dateList[i+1]).toInt()
                        val delta = (requiredInterval - interval).absoluteValue + 1
                        averageDelta += delta
                    }
                    averageDelta /= intervalCount
//                    if (dateList.size > 2) {
//                        print("${taskType.name} ${v?.type?.name} date list: $dateList: ")
//                    }
//                    println(averageDelta)
                    return 1_000_000 / averageDelta

                }
            )

    private fun workDayLimit(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.crew != null && task.date != null }
            .groupBy(PlannedTask::crew, PlannedTask::date, sum { task -> task.taskType?.durationHours ?: 0 })
            .filter { _, _, totalSum -> totalSum > 8 }
            .penalize("work hour limit", HardSoftScore.ofHard(1_000_000_000))

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

    private fun resourceLimit(constraintFactory: ConstraintFactory) =
        constraintFactory
            .from(PlannedTask::class.java)
            .filter { task -> task.crew != null && task.date != null }
            .groupBy(sumBigDecimal { task -> BigDecimal.valueOf(task.taskType?.moneyResources ?: 0.0) })
            .join(ScheduleParameters::class.java)
            .penalize(
                "resourceLimit",
                HardSoftScore.ONE_HARD,
                fun(totalResources, resourceLimit): Int {
                    if (totalResources > BigDecimal.valueOf(resourceLimit.resourceLimit ?: 0.0)) {
                        return 1_000_000_000
                    }
                    return 0
                },
            )
}