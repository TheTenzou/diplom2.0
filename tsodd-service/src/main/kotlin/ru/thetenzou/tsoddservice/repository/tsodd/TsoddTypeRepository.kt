package ru.thetenzou.tsoddservice.repository.tsodd

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.model.tsodd.TsoddType

@RepositoryRestResource
interface TsoddTypeRepository : JpaRepository<TsoddType, Long>