package ru.thetenzou.tsoddservice.model.schedule

import java.time.LocalDate
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

    @Column(name = "created_date")
    var createdDate: LocalDateTime,

    @Column(name = "start_date")
    var startDate: LocalDate,

    @Column(name = "end_date")
    var endDate: LocalDate,

    @OneToMany
    var scheduledTask: List<ScheduledTask>?,

)
