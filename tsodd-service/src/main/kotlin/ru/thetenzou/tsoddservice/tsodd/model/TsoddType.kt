package ru.thetenzou.tsoddservice.tsodd.model

import ru.thetenzou.tsoddservice.task.model.TaskGroup
import javax.persistence.*

@Entity
@Table(name = "tsodd_types")
data class TsoddType(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    @Column(name = "name")
    var name: String,
    @ManyToMany
//    @JoinColumn(name = "task_group_id", referencedColumnName = "id")
    var taskGroup: List<TaskGroup>?,
)
