package ru.thetenzou.tsoddservice.task.model

import com.fasterxml.jackson.annotation.JsonIgnore
import ru.thetenzou.tsoddservice.tsodd.model.TsoddGroup
import javax.persistence.*

/**
 * A TaskGroup is model that represent task group record from database
 *
 * @param id is id of the record
 * @param name name of the task group
 * @param tsoddGroup list of tsodd groups that this group of tasks can bee performed
 * @param taskTypes list tasks of this type
 */
@Entity
@Table(name = "task_group")
data class TaskGroup(

    /**
     * is id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    /**
     * name of the task group
     */
    @Column(name = "name")
    var name: String,

    /**
     * list of tsodd groups that this group of tasks can bee performed
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "taskGroup")
    var tsoddGroups: List<TsoddGroup>?,

    /**
     * list tasks of this type
     */
    @JsonIgnore
    @OneToMany(mappedBy = "taskGroup")
    var taskType: List<TaskType>,
)

