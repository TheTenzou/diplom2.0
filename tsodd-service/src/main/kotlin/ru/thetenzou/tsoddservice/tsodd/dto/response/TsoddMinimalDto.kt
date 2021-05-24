package ru.thetenzou.tsoddservice.tsodd.dto.response

import org.locationtech.jts.geom.GeometryCollection
import ru.thetenzou.tsoddservice.tsodd.model.Tsodd

/**
 * A TsoddMinimalDto is data transfer object for Tsodd model
 *
 * @param id id of the tsodd
 * @param typeName name of the tsodd type
 * @param groupName name of the tsodd group
 * @param coordinates tsodd coordinates
 */
data class TsoddMinimalDto(

    /**
     * id of the tsodd
     */
    val id: Long,

    /**
     * name of the tsodd type
     */
    val typeName: String,

    /**
     * name of the tsodd group
     */
    val groupName: String,

    /**
     * tsodd coordinates
     */
    val coordinates: GeometryCollection,
) {

    /**
     * Init TsoddMinimalDto base on given Tsodd
     */
    constructor(tsodd: Tsodd) : this(
        id = tsodd.id,
        typeName = tsodd.type.name,
        groupName = tsodd.type.tsoddGroup.name,
        coordinates = tsodd.coordinates
    )
}
