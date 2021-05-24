package ru.thetenzou.tsoddservice.schedule.model

import ru.thetenzou.tsoddservice.crew.model.Crew
import ru.thetenzou.tsoddservice.task.model.Task
import ru.thetenzou.tsoddservice.tsodd.model.Tsodd
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

    @ManyToOne
    @JoinColumn(name = "crew_id")
    var crew: Crew,
)
