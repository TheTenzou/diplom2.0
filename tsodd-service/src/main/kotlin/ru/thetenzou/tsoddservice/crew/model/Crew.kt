package ru.thetenzou.tsoddservice.crew.model

import javax.persistence.*

/**
 * A Crew is model that represent crew record from database
 *
 * @param id is id of the record
 * @param name name of the crew
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
)
