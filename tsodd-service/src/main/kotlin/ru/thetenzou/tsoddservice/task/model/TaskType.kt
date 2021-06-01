package ru.thetenzou.tsoddservice.task.model

import ru.thetenzou.tsoddservice.crew.model.Crew
import javax.persistence.*

/**
 * A TaskType is model that represent task type record from database
 *
 * @param id is id of the record
 * @param name name of the task type
 * @param taskGroup task group that this task type belongs
 * @param timeIntervalInDays recommended interval in days between two tasks
 * @param durationHours duration of this task in hours
 * @param moneyResources money that required to complete task
 * @param effectiveness effectiveness of this task
 * @param crewList list of crews that can perform this task
 */
@Entity
@Table(name = "task_types")
data class TaskType(

    /**
     * is id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    /**
     * name of the task type
     */
    @Column(name = "name")
    var name: String,

    /**
     * task group that this task type belongs
     */
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    var taskGroup: TaskGroup,

    /**
     * recommended interval in days between two tasks
     */
    @Column(name = "time_interval")
    var timeIntervalInDays: Int,

    /**
     * duration of this task in hours
     */
    @Column(name = "duration_hours")
    var durationHours: Int,

    /**
     * money that required to complete task
     */
    @Column(name = "money")
    var moneyResources: Double,

    /**
     * effectiveness of this task
     */
    @Column(name = "effectiveness")
    var effectiveness: Int,

    /**
     * list of crews that can perform this task
     */
    @ManyToMany
    var crewList: List<Crew>,
)
