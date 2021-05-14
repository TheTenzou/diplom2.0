package ru.thetenzou.tsoddservice.model.schedule

import ru.thetenzou.tsoddservice.model.task.Task
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "scheduled_tasks")
data class ScheduledTask(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    var schedule: Schedule?,
    @Column(name = "date_time")
    var dataTime: LocalDateTime,
    @ManyToOne
    @JoinColumn(name = "tsodd_id")
    var tsodd: Tsodd,
    @ManyToOne
    @JoinColumn(name = "task_id")
    var task: Task,
    @Transient
    val selected: Boolean,
)
