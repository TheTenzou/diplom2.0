package ru.thetenzou.tsoddservice.model.schedule

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable
import ru.thetenzou.tsoddservice.model.task.Task
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import java.time.LocalDate
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

    @Column(name = "date")
    var date: LocalDate?,

    @ManyToOne
    @JoinColumn(name = "tsodd_id")
    var tsodd: Tsodd,

    @ManyToOne
    @JoinColumn(name = "task_id")
    var task: Task,
)
