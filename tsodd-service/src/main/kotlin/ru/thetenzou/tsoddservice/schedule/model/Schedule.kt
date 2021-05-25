package ru.thetenzou.tsoddservice.schedule.model

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

/**
 * A Schedule is model that represent schedule record from database
 *
 * @param id is id of the record
 * @param name name of the schedule
 * @param createdDate date of creation
 * @param startDate date when schedule begins
 * @param endDate date when schedule ends
 * @param status describe is schedule in the process of generating
 * @param scheduledTask list of scheduled tasks
 */
@Entity
@Table(name = "schedules")
data class Schedule(

    /**
     * is id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    /**
     * name of the schedule
     */
    @Column(name = "name")
    var name: String,

    /**
     *  date of creation
     */
    @Column(name = "created_date")
    var createdDate: LocalDateTime,

    /**
     *  date when schedule begins
     */
    @Column(name = "start_date")
    var startDate: LocalDate,

    /**
     *  date when schedule ends
     */
    @Column(name = "end_date")
    var endDate: LocalDate,

    /**
     * status describe is schedule in the process of generating
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: ScheduleStatus,

    /**
     *  list of scheduled tasks
     */
    @OneToMany(mappedBy = "schedule", cascade = [CascadeType.ALL])
    var scheduledTask: List<ScheduledTask>?,
)
