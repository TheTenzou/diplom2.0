package ru.thetenzou.tsoddservice.crew.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.crew.model.Crew

@RepositoryRestResource
interface CrewRepository : JpaRepository<Crew, Long>