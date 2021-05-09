package ru.thetenzou.tsoddservice.model.task

import javax.persistence.*

@Entity
@Table(name = "task_group")
data class TaskGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    @Column(name = "name")
    var name: String
)

