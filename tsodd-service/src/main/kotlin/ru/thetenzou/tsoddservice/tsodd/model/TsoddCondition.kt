package ru.thetenzou.tsoddservice.tsodd.model

import javax.persistence.*

/**
 * A TsoddCondition is model that represent tsodd condition record from database
 *
 * @param id is id of the record
 * @param name name of the tsodd condition
 */
@Entity
@Table(name = "tsodd_conditions")
data class TsoddCondition(

    /**
     * is id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    /**
     *  name of the tsodd condition
     */
    @Column(name = "name")
    var name: String,
)
