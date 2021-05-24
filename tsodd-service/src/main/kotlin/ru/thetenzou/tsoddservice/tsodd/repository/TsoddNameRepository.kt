package ru.thetenzou.tsoddservice.tsodd.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.tsodd.model.TsoddType

@RepositoryRestResource
interface TsoddNameRepository : JpaRepository<TsoddType, Long>