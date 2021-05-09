package ru.thetenzou.tsoddservice.model.schedule

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "schedules")
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    @Column(name = "name")
    var name: String,
    @Column(name = "data_time")
    var dateTime: LocalDateTime,
    @OneToMany
    var scheduledTask: List<ScheduledTask>,
)
