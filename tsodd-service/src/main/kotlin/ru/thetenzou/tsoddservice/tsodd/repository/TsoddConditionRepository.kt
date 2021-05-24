package ru.thetenzou.tsoddservice.tsodd.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.tsodd.model.TsoddCondition

@RepositoryRestResource
interface TsoddConditionRepository : JpaRepository<TsoddCondition, Long>