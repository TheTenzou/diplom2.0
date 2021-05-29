package ru.thetenzou.tsoddservice.crew.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.thetenzou.tsoddservice.crew.dto.response.CrewDto
import ru.thetenzou.tsoddservice.crew.service.CrewService

/**
 * A ScheduleController is endpoint for crews
 */
@RestController
@RequestMapping("/api/tsodd/v1/crew")
class CrewController(
    private val crewService: CrewService,
) {

    /**
     * getAllCrew return all crews
     *
     * @return list of all crews
     */
    @GetMapping
    fun getAllCrew():ResponseEntity<List<CrewDto>> {
        val crewList = crewService.getAllCrew()
        return ResponseEntity(crewList, HttpStatus.OK)
    }
}