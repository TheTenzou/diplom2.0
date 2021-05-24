package ru.thetenzou.tsoddservice.tsodd.model

import javax.persistence.*

/**
 * A tsoddType is model that represent tsodd type record from database
 *
 * @param id is id of the record
 * @param name name of the tsodd type
 * @param tsoddGroup tsodd group that this tsodd belongs to
 */
@Entity
@Table(name = "tsodd_types")
data class TsoddType(

    /**
     * is id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    /**
     * name of the tsodd type
     */
    @Column(name = "name")
    var name: String,

    /**
     * tsodd group that this tsodd belongs to
     */
    @ManyToOne
    @JoinColumn(name = "group_id")
    var tsoddGroup: TsoddGroup,
)
