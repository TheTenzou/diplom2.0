package ru.thetenzou.tsoddservice.repository.tsodd

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.model.tsodd.TsoddCondition

@RepositoryRestResource
interface TsoddConditionRepository : JpaRepository<TsoddCondition, Long>