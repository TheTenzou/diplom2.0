package ru.thetenzou.tsoddservice.tsodd.model

import org.locationtech.jts.geom.GeometryCollection
import javax.persistence.*

/**
 * A Tsodd is model that represent tsodd record from database
 *
 * @param id is id of the record
 * @param type type of this tsodd
 * @param visibility describe how visible this tsodd is. Max value 1, min value 0
 * @param condition current condition of the tsodd
 * @param coordinates collection of the points that describe tsodd location
 */
@Entity
@Table(name = "tsodds")
data class Tsodd(

    /**
     * is id of the record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    /**
     * type of this tsodd
     */
    @ManyToOne
    @JoinColumn(name = "tsodd_type_id")
    var type: TsoddType,

    /**
     * describe how visible this tsodd is. Max value 1, min value 0
     */
    @Column(name = "visibility")
    var visibility: Double,

    /**
     * current condition of the tsodd
     */
    @ManyToOne
    @JoinColumn(name = "condition_id")
    var condition: TsoddCondition?,
    /**
     * collection of the points that describe tsodd location
     */
    @Column(name = "coordinates")
    var coordinates: GeometryCollection,
)
