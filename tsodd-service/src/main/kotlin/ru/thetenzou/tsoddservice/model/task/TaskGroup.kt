package ru.thetenzou.tsoddservice.model.task

import ru.thetenzou.tsoddservice.model.tsodd.TsoddType
import javax.persistence.*

@Entity
@Table(name = "task_group")
data class TaskGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    @Column(name = "name")
    var name: String,
    @OneToOne(mappedBy = "taskGroup")
    var tsoddType: TsoddType?,
    @OneToMany(mappedBy = "taskGroup")
    var tasks: List<Task>,
)

