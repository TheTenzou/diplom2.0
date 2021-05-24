package ru.thetenzou.tsoddservice.schedule.model

import ru.thetenzou.tsoddservice.crew.model.Crew
import ru.thetenzou.tsoddservice.task.model.TaskType
import ru.thetenzou.tsoddservice.tsodd.model.Tsodd
import java.time.LocalDate
import javax.persistence.*

/**
 * A ScheduledTask is model that represent scheduled task record from database
 *
 * @param id is id of the record
 * @param schedule schedule that this task belongs
 * @param date when this tasks will be or was performed
 * @param tsodd target of the task
 * @param taskType type of the task
 * @param crew that will perform the task
 */
@Entity
@Table(name = "scheduled_tasks")
data class ScheduledTask(

    /**
     * is id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    /**
     * schedule that this task belongs
     */
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    var schedule: Schedule?,

    /**
     * date when this tasks will be or was performed
     */
    @Column(name = "date")
    var date: LocalDate?,

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

    /**
     * crew that will perform the task
     */
    @ManyToOne
    @JoinColumn(name = "crew_id")
    var crew: Crew,
)
