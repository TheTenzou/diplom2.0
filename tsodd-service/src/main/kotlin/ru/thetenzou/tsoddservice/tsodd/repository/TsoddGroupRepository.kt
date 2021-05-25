package ru.thetenzou.tsoddservice.tsodd.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.tsodd.model.TsoddGroup

@RepositoryRestResource
interface TsoddGroupRepository : JpaRepository<TsoddGroup, Long>