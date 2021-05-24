package ru.thetenzou.tsoddservice.tsodd.model

import ru.thetenzou.tsoddservice.task.model.TaskGroup
import javax.persistence.*

/**
 * A TsoddGroup is model that represent tsodd group record from database
 *
 * @param id id of the record
 * @param name name of the group
 * @param taskGroups list of available task groups
 */
@Entity
@Table(name = "tsodd_groups")
data class TsoddGroup(

    /**
     * is id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    /**
     * name of the group
     */
    @Column(name = "name")
    var name: String,

    /**
     * list of available task groups
     */
    @ManyToMany
    var taskGroup: List<TaskGroup>?,
)
