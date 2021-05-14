package ru.thetenzou.tsoddservice.model.tsodd

import ru.thetenzou.tsoddservice.model.task.TaskGroup
import javax.persistence.*

@Entity
@Table(name = "tsodd_types")
data class TsoddType(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    @Column(name = "name")
    var name: String,
    @OneToOne
    @JoinColumn(name = "task_group_id", referencedColumnName = "id")
    var taskGroup: TaskGroup?,
)
