package ru.thetenzou.tsoddservice.solver

import org.optaplanner.core.impl.domain.valuerange.AbstractCountableValueRange
import org.optaplanner.core.impl.domain.valuerange.util.ValueRangeIterator
import org.optaplanner.core.impl.solver.random.RandomUtils
import ru.thetenzou.tsoddservice.model.schedule.ScheduledTask
import  ru.thetenzou.tsoddservice.model.task.Task
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import ru.thetenzou.tsoddservice.model.tsodd.TsoddType
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.NoSuchElementException

class ScheduledTaskValueRange(
    private val tsoddList: List<Tsodd>,
    private val taskMap: Map<TsoddType, List<Task>>,
    private val startDate: LocalDateTime,
    private val endDate: LocalDateTime,
) : AbstractCountableValueRange<ScheduledTask>() {


    private val size: Long =
        tsoddList.sumOf { taskMap[it.name.tsoddType]?.size ?: 0 } * ChronoUnit.DAYS.between(startDate, endDate)

    override fun getSize() = size

    // TODO fix this shit
    override fun contains(scheduledTask: ScheduledTask) =
        taskMap[scheduledTask.tsodd.name.tsoddType]?.contains(scheduledTask.task) ?: false &&
                tsoddList.contains(scheduledTask.tsodd) &&
                scheduledTask.dataTime > startDate &&
                scheduledTask.dataTime < endDate

    // TODO fix this shit too
    override fun get(index: Long): ScheduledTask {
        val tsoddIndex = (index % tsoddList.size).toInt()
        val taskIndex =
            (index / tsoddList.size % (taskMap[tsoddList[tsoddIndex].name.tsoddType]?.size
                ?: throw NoSuchElementException())).toInt()
        var days = index / tsoddList.size / (taskMap[tsoddList[tsoddIndex].name.tsoddType]?.size
            ?: throw NoSuchElementException())

        return ScheduledTask(
            id = null,
            schedule = null,
            task = taskMap[tsoddList[taskIndex].name.tsoddType]?.get(taskIndex) ?: throw NoSuchElementException(),
            tsodd = tsoddList[tsoddIndex],
            dataTime = startDate.plusDays(days),
            selected = false,
        )
    }

    override fun createOriginalIterator(): MutableIterator<ScheduledTask> =
        OriginalScheduledTaskIterator()

    private inner class OriginalScheduledTaskIterator : ValueRangeIterator<ScheduledTask>() {

        private var scheduledTaskIndex = 0L

        override fun hasNext() =
            scheduledTaskIndex < size

        override fun next(): ScheduledTask {
            if (scheduledTaskIndex >= size) {
                throw NoSuchElementException()
            }
            val scheduledTask = get(scheduledTaskIndex)

            scheduledTaskIndex++

            return scheduledTask
        }

    }

    override fun createRandomIterator(workingRandom: Random): MutableIterator<ScheduledTask> {
        return RandomScheduledTaskIterator(workingRandom)
    }

    private inner class RandomScheduledTaskIterator(private val workingRandom: Random) :
        ValueRangeIterator<ScheduledTask>() {

        private val size = getSize()

        override fun hasNext() = size > 0

        override fun next(): ScheduledTask {
            if (size <= 0) {
                throw NoSuchElementException()
            }

            val index = RandomUtils.nextLong(workingRandom, size)
            return get(index)
        }

    }
}