package ru.thetenzou.tsoddservice.model.task

import javax.persistence.*

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    @Column(name = "name")
    var name: String,
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    var taskGroup: TaskGroup,
    @Column(name = "time_interval")
    var timeIntervalInDays: Int,
    @Column(name = "duration_hours")
    var durationHours: Int,
    @Column(name = "money")
    var moneyResources: Double,
    @Column(name = "effectiveness")
    var effectiveness: Int,
)
