package ru.thetenzou.tsoddservice.task.model

import ru.thetenzou.tsoddservice.tsodd.model.Tsodd
import java.time.LocalDateTime
import javax.persistence.*

/**
 * A Task is model that represent task record from database
 *
 * @param id is id of the record
 * @param date date when task was performed
 * @param tsodd target of the task
 * @param taskType type of the task
 */
@Entity
@Table(name = "tasks")
data class Task(

    /**
     * is id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    /**
     * date when task was performed
     */
    @Column(name = "date")
    var date: LocalDateTime,

    /**
     * target of the task
     */
    @ManyToOne
    @JoinColumn(name = "tsodd_id")
    var tsodd: Tsodd,

    /**
     * type of the task
     */
    @ManyToOne
    @JoinColumn(name = "task_type_id")
    var taskType: TaskType,
)
