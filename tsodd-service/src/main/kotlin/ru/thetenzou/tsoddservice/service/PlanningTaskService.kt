package ru.thetenzou.tsoddservice.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.thetenzou.tsoddservice.model.schedule.Schedule
import ru.thetenzou.tsoddservice.model.schedule.ScheduledTask
import ru.thetenzou.tsoddservice.model.solver.PlanningTask
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import ru.thetenzou.tsoddservice.repository.schedule.ScheduledTaskRepository
import ru.thetenzou.tsoddservice.repository.task.TaskRepository
import ru.thetenzou.tsoddservice.repository.tsodd.TsoddRepository
import java.util.stream.Collectors

/**
 * PlanningTaskService manage
 * TODO complete comment
 */
@Service
@Transactional
class PlanningTaskService(
    private val tsoddRepository: TsoddRepository,
    private val scheduledTaskRepository: ScheduledTaskRepository,
) {

    fun getPlanningTasks(days: Long): List<PlanningTask> {
        logger.info("Possible list of task has been generated for $days days interval")
        val tsoddList = tsoddRepository.findAll()

        val planningTaskList = ArrayList<PlanningTask>()

        tsoddList.forEach {
            planningTaskList.addAll(getPlanningTasksForTsodd(it, days))
        }

        return planningTaskList
    }

    fun save(schedule: Schedule, tasks: List<PlanningTask>) {

        val validTasks = tasks.filter { it.date != null && it.crew != null }

        val scheduledTaskList =
            validTasks.map { ScheduledTask(id = 0L, schedule, it.date, it.tsodd!!, it.task!!, it.crew!!) }

        scheduledTaskRepository.saveAll(scheduledTaskList)
    }

    private fun getPlanningTasksForTsodd(tsodd: Tsodd, days: Long): List<PlanningTask> {
        val planningTaskList = ArrayList<PlanningTask>()

        val taskList =
            tsodd.name.tsoddType.taskGroup?.stream()?.flatMap { it.tasks.stream() }?.collect(Collectors.toList())
                ?: return emptyList()

        for (task in taskList) {
            for (i in 0..(days / task.timeIntervalInDays)) {
                planningTaskList.add(PlanningTask(tsodd, task, null, null))
            }
        }
        return planningTaskList
    }

    companion object {
        private val logger = LoggerFactory.getLogger(PlanningTaskService::class.java)
    }
}