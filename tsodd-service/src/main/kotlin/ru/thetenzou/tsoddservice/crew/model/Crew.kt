package ru.thetenzou.tsoddservice.crew.model

import ru.thetenzou.tsoddservice.task.model.TaskType
import javax.persistence.*

/**
 * A Crew is model that represent crew record from database
 *
 * @param id is id of the record
 * @param name name of the crew
 * @param taskTypeList last of task this crew can perform
 */
@Entity
@Table(name = "crews")
data class Crew(

    /**
     * is id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    /**
     * name of the crew
     */
    @Column(name = "name")
    var name: String,

    /**
     * last of task this crew can perform
     */
    @ManyToMany(fetch = FetchType.EAGER)
    var taskTypeList: List<TaskType>,
)
