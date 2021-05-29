package ru.thetenzou.tsoddservice.crew.service

import org.springframework.stereotype.Service
import ru.thetenzou.tsoddservice.crew.dto.response.CrewDto
import ru.thetenzou.tsoddservice.crew.repository.CrewRepository

@Service
class CrewService(
    private val crewRepository: CrewRepository,
) {
    fun getAllCrew():List<CrewDto> {
        val crewList = crewRepository.findAll()

        return crewList.map { crew -> CrewDto(crew) }
    }
}