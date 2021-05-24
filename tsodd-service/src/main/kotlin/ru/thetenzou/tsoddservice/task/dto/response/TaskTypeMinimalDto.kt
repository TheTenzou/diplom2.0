package ru.thetenzou.tsoddservice.task.dto.response

import ru.thetenzou.tsoddservice.task.model.TaskType

/**
 * A TaskTypeMinimalDto is data transfer object for model TaskType
 *
 * @param id id of the task
 * @param name name of the task
 * @param groupName name of the group
 */
data class TaskTypeMinimalDto(

    /**
     * id of the task
     */
    val id: Long,

    /**
     * name of the task
     */
    val name: String,

    /**
     * name of the group
     */
    val groupName: String,
) {

    /**
     * Init TaskTypeMinimalDto base on given taskType
     */
    constructor(taskType: TaskType) : this(
        id = taskType.id,
        name = taskType.name,
        groupName = taskType.taskGroup.name,
    )
}
