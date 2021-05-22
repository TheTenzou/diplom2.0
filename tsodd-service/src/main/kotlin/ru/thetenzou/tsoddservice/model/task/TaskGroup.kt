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
    @ManyToMany(mappedBy = "taskGroup")
    var tsoddType: List<TsoddType>?,
    @OneToMany(mappedBy = "taskGroup", fetch = FetchType.EAGER)
    var tasks: List<Task>,
)

