package ru.thetenzou.tsoddservice.repository.crew

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.model.crew.Crew

@RepositoryRestResource
interface CrewRepository : JpaRepository<Crew, Long>