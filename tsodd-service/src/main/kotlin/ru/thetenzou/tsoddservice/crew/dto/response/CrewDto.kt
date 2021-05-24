package ru.thetenzou.tsoddservice.crew.dto.response

import ru.thetenzou.tsoddservice.crew.model.Crew

/**
 * A CrewDto is data transfer object for crew model
 */
data class CrewDto(
    val id: Long,
    val name: String,
) {

    /**
     * Init CrewDto base on given crew
     */
    constructor(crew: Crew) : this(
        id = crew.id,
        name = crew.name,
    )
}
