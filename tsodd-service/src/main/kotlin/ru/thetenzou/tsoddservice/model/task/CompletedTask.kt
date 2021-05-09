package ru.thetenzou.tsoddservice.model.task

import ru.thetenzou.tsoddservice.model.tsodd.Tsodd
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "completed_tasks")
data class CompletedTask(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    @Column(name = "date")
    var date: LocalDateTime,
    @ManyToOne
    @JoinColumn(name = "tsodd_id")
    var tsodd: Tsodd,
    @ManyToOne
    @JoinColumn(name = "task_id")
    var task: Task,
)
