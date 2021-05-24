package ru.thetenzou.tsoddservice.schedule.service.solver

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.thetenzou.tsoddservice.schedule.model.Schedule
import ru.thetenzou.tsoddservice.schedule.model.ScheduledTask
import ru.thetenzou.tsoddservice.schedule.model.solver.PlannedTask
import ru.thetenzou.tsoddservice.tsodd.model.Tsodd
import ru.thetenzou.tsoddservice.schedule.repository.ScheduledTaskRepository
import ru.thetenzou.tsoddservice.tsodd.repository.TsoddRepository
import java.util.stream.Collectors

/**
 * A PlannedTaskService manages PlannedTask entities
 * required for TsoddScheduleProblemService
 */
@Service
@Transactional
class PlannedTaskService(
    private val tsoddRepository: TsoddRepository,
    private val scheduledTaskRepository: ScheduledTaskRepository,
) {

    /**
     * getPlanningTasks create list of planned tasks base on amount of days given
     *
     * @param days length of time interval in days
     *
     * @return list of planned tasks
     */
    fun getPlanningTasks(days: Long): List<PlannedTask> {
        logger.info("Possible list of task has been generated for $days days interval")

        val tsoddList = tsoddRepository.findAll()
        val plannedTaskList = ArrayList<PlannedTask>()

        tsoddList.forEach {
            plannedTaskList.addAll(getPlannedTasksForTsodd(it, days))
        }

        return plannedTaskList
    }

    /**
     * This function save planned tasks to given schedule in database
     *
     * @param schedule specify schedule
     * @param tasks list of planned tasks to save
     */
    fun save(schedule: Schedule, tasks: List<PlannedTask>) {

        val validTasks = tasks.filter { it.date != null && it.crew != null }

        val scheduledTaskList =
            validTasks.map { ScheduledTask(id = 0L, schedule, it.date, it.tsodd!!, it.taskType!!, it.crew!!) }

        scheduledTaskRepository.saveAll(scheduledTaskList)
    }

    /**
     * getPlannedTasksForTsodd create planned tasks for given tsodd for given time period length
     *
     * @param tsodd
     * @param days length of time interval in days
     *
     * @return list of planned task
     */
    private fun getPlannedTasksForTsodd(tsodd: Tsodd, days: Long): List<PlannedTask> {
        val plannedTaskList = ArrayList<PlannedTask>()

        val taskList =
            tsodd.type.tsoddGroup.taskGroup?.stream()?.flatMap { it.taskType.stream() }?.collect(Collectors.toList())
                ?: return emptyList()

        for (task in taskList) {
            for (i in 0..(days / task.timeIntervalInDays)) {
                plannedTaskList.add(PlannedTask(tsodd, task, null, null))
            }
        }

        return plannedTaskList
    }

    companion object {
        private val logger = LoggerFactory.getLogger(PlannedTaskService::class.java)
    }
}