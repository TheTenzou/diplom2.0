package ru.thetenzou.tsoddservice.repository.schedule

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface ScheduleRepository : JpaRepository<ScheduleRepository, Long>