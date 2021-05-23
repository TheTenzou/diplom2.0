package ru.thetenzou.tsoddservice.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.thetenzou.tsoddservice.model.solver.PlanningTask
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import ru.thetenzou.tsoddservice.repository.task.TaskRepository
import ru.thetenzou.tsoddservice.repository.tsodd.TsoddRepository
import java.util.stream.Collectors

@Service
@Transactional
class PlanningTaskService(
    private val tsoddRepository: TsoddRepository,
) {

    fun getPlanningTasks(days: Long): List<PlanningTask> {
        logger.info("Possible list of task have been generated for $days days interval")
        val tsoddList = tsoddRepository.findAll()

        val planningTaskList = ArrayList<PlanningTask>()

        tsoddList.forEach {
            planningTaskList.addAll(getPlanningTasksForTsodd(it, days))
        }

        return planningTaskList
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